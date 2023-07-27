package com.commencis.deliveryservice.service;

import brave.Span;
import brave.Tracer;
import com.commencis.deliveryservice.dto.*;
import com.commencis.deliveryservice.model.Delivery;
import com.commencis.deliveryservice.repository.DeliveryRepository;
import com.commencis.deliveryservice.utils.GenericFilterCriteriaBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final FilterBuilderService filterBuilderService;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;

    public List<DeliveryResponse> getDeliveries(int pageNumber, int pageSize, String filterOr, String filterAnd, String orders) {
        PageResponse<Delivery> response = new PageResponse<>();

        Pageable pageable = filterBuilderService.getPageable(pageSize, pageNumber, orders);
        GenericFilterCriteriaBuilder filterCriteriaBuilder = new GenericFilterCriteriaBuilder();

        List<FilterCondition> andConditions = filterBuilderService.createFilterCondition(filterAnd);
        List<FilterCondition> orConditions = filterBuilderService.createFilterCondition(filterOr);

        Query query = filterCriteriaBuilder.addCondition(andConditions, orConditions);
        Page<Delivery> pages = deliveryRepository.findAll(query, pageable);
        response.setPageStats(pages, pages.getContent());

        return pages.stream().map(this::mapToDeliveryResponse).toList();
    }

    public DeliveryResponse getDelivery(String deliveryId) {
        Delivery delivery = deliveryRepository.findById(deliveryId).get();

        return mapToDeliveryResponse(delivery);
    }

    public Delivery createDelivery(DeliveryRequest deliveryRequest) {
        CompanyResponse companyResponse;

        Span companyServiceLookup = tracer.nextSpan().name("CompanyServiceLookup");

        try (Tracer.SpanInScope isLookup = tracer.withSpanInScope(companyServiceLookup.start())) {

            companyServiceLookup.tag("call", "company-service");

            companyResponse = webClientBuilder.build().get()
                    .uri("http://company-service/api/companies/" + deliveryRequest.getCompanyId())
                    .retrieve()
                    .bodyToMono(CompanyResponse.class)
                    .block();
        } finally {
            companyServiceLookup.flush();
        }

        if (companyResponse == null || companyResponse.getId() == null) {
            throw new RuntimeException("Has not available Company Id");
        }

        List<Delivery> hasAnyTrackingNumbers = deliveryRepository.findByTrackingNumber(deliveryRequest.getTrackingNumber());
        if ((long) hasAnyTrackingNumbers.size() > 0) {
            throw new RuntimeException("Tracking Number has already been generated");
        }

        Delivery delivery = Delivery.builder()
                .name(deliveryRequest.getName())
                .companyId(companyResponse.getId())
                .trackingNumber(deliveryRequest.getTrackingNumber())
                .toAddress(deliveryRequest.getToAddress())
                .build();

        log.info("Delivery {} is saved", delivery.getId());

        return deliveryRepository.save(delivery);
    }

    public void updateDelivery(DeliveryRequest deliveryRequest, String deliveryId) {
        if (deliveryRequest.getCompanyId() == null) {
            return;
        }

        Optional<Delivery> delivery = deliveryRepository.findById(deliveryId);

        if (delivery.isPresent()) {
            Delivery deliveryData = delivery.get();
            deliveryData.setName(deliveryRequest.getName());
            deliveryData.setTrackingNumber(deliveryRequest.getTrackingNumber());
            deliveryData.setToAddress(deliveryRequest.getToAddress());

            deliveryRepository.save(deliveryData);
        }

        log.info("Delivery {} is updated", deliveryId);
    }

    public void deleteDelivery(String deliveryId) {
        deliveryRepository.deleteById(deliveryId);
    }

    private DeliveryResponse mapToDeliveryResponse(Delivery delivery) {
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .name(delivery.getName())
                .trackingNumber(delivery.getTrackingNumber())
                .toAddress(delivery.getToAddress())
                .createdDate(delivery.getCreatedDate())
                .updatedDate(delivery.getUpdatedDate())
                .build();
    }
}
