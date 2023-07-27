package com.commencis.deliveryservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(value = "delivery")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Delivery {
    @Id
    private String id;
    private String name;

    @Indexed(unique = true)
    private String trackingNumber;

    private String companyId;

    private String toAddress;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date updatedDate;
}