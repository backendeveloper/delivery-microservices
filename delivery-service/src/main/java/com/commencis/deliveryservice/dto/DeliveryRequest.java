package com.commencis.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryRequest {
    private String name;

    @NotBlank(message = "Company Id is not found")
    private String companyId;
    private String trackingNumber;
    private String toAddress;
}
