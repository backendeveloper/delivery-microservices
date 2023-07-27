package com.commencis.companyservice.service;

import com.commencis.companyservice.dto.CompanyRequest;
import com.commencis.companyservice.dto.CompanyResponse;
import com.commencis.companyservice.model.Company;
import com.commencis.companyservice.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CompanyService {
    private final CompanyRepository companyRepository;

    public List<CompanyResponse> getAllCompanies() {
        List<Company> companies = companyRepository.findAll();

        return companies.stream().map(this::mapToCompanyResponse).toList();
    }

    public CompanyResponse getCompany(String companyId) {
        Company company = companyRepository.findById(companyId).get();

        return mapToCompanyResponse(company);
    }

    public void createCompany(CompanyRequest companyRequest) {
        Company company = Company.builder()
                .name(companyRequest.getName())
                .description(companyRequest.getDescription())
                .address(companyRequest.getAddress())
                .build();

        companyRepository.save(company);
        log.info("Company {} is saved", company.getId());
    }

    public void updateCompany(CompanyRequest companyRequest, String companyId) {
        Optional<Company> company = companyRepository.findById(companyId);

        if (company.isPresent()) {
            Company companyData = company.get();
            companyData.setName(companyRequest.getName());
            companyData.setDescription(companyRequest.getDescription());
            companyData.setAddress(companyRequest.getAddress());

            companyRepository.save(companyData);
        }

        log.info("Company {} is updated", companyId);
    }

    public void deleteCompany(String id) {
        companyRepository.deleteById(id);
    }

    private CompanyResponse mapToCompanyResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .description(company.getDescription())
                .address(company.getAddress())
                .build();
    }
}
