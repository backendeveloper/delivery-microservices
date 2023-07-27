package com.commencis.companyservice.repository;

import com.commencis.companyservice.model.Company;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CompanyRepository extends MongoRepository<Company, String> {
}
