package com.commencis.deliveryservice.service;

import com.commencis.deliveryservice.dto.FilterCondition;
import com.commencis.deliveryservice.enums.FilterOperationEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class FilterBuilderService {
    private static final int DEFAULT_SIZE_PAGE = 10;

    public List<FilterCondition> createFilterCondition(String criteria) {
        List<FilterCondition> filters = new ArrayList<>();

        if (criteria != null && !criteria.isEmpty()) {
            final String FILTER_SHEARCH_DELIMITER = "&";
            final String FILTER_CONDITION_DELIMITER = "\\|";

            List<String> values = split(criteria, FILTER_SHEARCH_DELIMITER);
            if (!values.isEmpty()) {
                values.forEach(x -> {
                    List<String> filter = split(x, FILTER_CONDITION_DELIMITER);
                    if (FilterOperationEnum.fromValue(filter.get(1)) != null) {
                        filters.add(new FilterCondition(filter.get(0), FilterOperationEnum.fromValue(filter.get(1)), filter.get(2)));
                    }
                });
            }
        }

        return filters;
    }

    public PageRequest getPageable(int size, int page, String order) {
        int pageSize = (size <= 0) ? DEFAULT_SIZE_PAGE : size;
        int currentPage = (page <= 0) ? 1 : page;

        if (order != null && !order.isEmpty()) {
            final String FILTER_CONDITION_DELIMITER = "\\|";

            List<String> values = split(order, FILTER_CONDITION_DELIMITER);
            String column = values.get(0);
            String sortDirection = values.get(1);

            if (sortDirection.equalsIgnoreCase("ASC")) {
                return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.ASC, column));
            } else if (sortDirection.equalsIgnoreCase("DESC")) {
                return PageRequest.of((currentPage - 1), pageSize, Sort.by(Sort.Direction.DESC, column));
            } else {
                throw new IllegalArgumentException(String.format("Value for param 'order' is not valid : %s , must be 'asc' or 'desc'", sortDirection));
            }

        } else {
            return PageRequest.of((currentPage - 1), pageSize);
        }
    }

    private static List<String> split(String search, String delimiter) {
        return Stream.of(search.split(delimiter)).collect(Collectors.toList());
    }
}
