package com.commencis.companyservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "company")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Company {
    @Id
    private String id;
    private String name;
    private String description;
    private String address;
}
