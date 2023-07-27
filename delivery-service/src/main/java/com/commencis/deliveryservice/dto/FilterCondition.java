package com.commencis.deliveryservice.dto;

import com.commencis.deliveryservice.enums.FilterOperationEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class FilterCondition {
    private String field;
    private FilterOperationEnum operator;
    private Object value;
}
