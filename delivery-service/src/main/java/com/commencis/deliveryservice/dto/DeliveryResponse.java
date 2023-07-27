package com.commencis.deliveryservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryResponse {
    private String id;
    private String name;
    private String companyId;
    private String trackingNumber;
    private String toAddress;
    private Date createdDate;
    private Date updatedDate;
}
