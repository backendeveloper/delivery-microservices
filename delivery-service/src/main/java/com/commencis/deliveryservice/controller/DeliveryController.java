package com.commencis.deliveryservice.controller;

import com.commencis.deliveryservice.dto.DeliveryRequest;
import com.commencis.deliveryservice.dto.DeliveryResponse;
import com.commencis.deliveryservice.model.Delivery;
import com.commencis.deliveryservice.service.DeliveryService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
@Slf4j
@Validated
public class DeliveryController {

    @Autowired
    private final DeliveryService deliveryService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<DeliveryResponse> getDeliveries(
            @RequestParam(value = "page", defaultValue = "0") int pageNumber,
            @RequestParam(value = "size", defaultValue = "10") int pageSize,
            @RequestParam(value = "filterOr", required = false) String filterOr,
            @RequestParam(value = "filterAnd", required = false) String filterAnd,
            @RequestParam(value = "orders", required = false) String orders
    ) {
        return deliveryService.getDeliveries(pageNumber, pageSize, filterOr, filterAnd, orders);
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public DeliveryResponse getDelivery(@PathVariable("id") String deliveryId) {
        return deliveryService.getDelivery(deliveryId);
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CircuitBreaker(name = "company", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "company")
    @Retry(name = "company")
    public CompletableFuture<Delivery> createDelivery(@RequestBody DeliveryRequest deliveryRequest) {
        log.info("Placing Order");

        return CompletableFuture.supplyAsync(() -> deliveryService.createDelivery(deliveryRequest));
    }

    public CompletableFuture<String> fallbackMethod(DeliveryRequest deliveryRequest, RuntimeException runtimeException) {
        log.info("Cannot Place Order Executing Fallback logic");
        return CompletableFuture.supplyAsync(() -> "Oops! Something went wrong, please order after some time!");
    }

//    @PostMapping()
//    public ResponseEntity<Delivery> createDelivery(@Valid @RequestBody DeliveryRequest deliveryRequest) {
//        return new ResponseEntity<>(deliveryService.createDelivery(deliveryRequest), HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateDelivery(@RequestBody DeliveryRequest deliveryRequest,
                                 @PathVariable("id") String deliveryId) {
        deliveryService.updateDelivery(deliveryRequest, deliveryId);

        return "Updated Successfully";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteDelivery(@PathVariable("id") String deliveryId) {
        deliveryService.deleteDelivery(deliveryId);

        return "Deleted Successfully";
    }
}
