package com.commencis.companyservice.controller;

import com.commencis.companyservice.dto.CompanyRequest;
import com.commencis.companyservice.dto.CompanyResponse;
import com.commencis.companyservice.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CompanyResponse> getAllCompanies() {
        return companyService.getAllCompanies();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CompanyResponse getCompany(@PathVariable("id") String companyId)
    {
        return companyService.getCompany(companyId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createCompany(@RequestBody CompanyRequest companyRequest) {
        companyService.createCompany(companyRequest);

        return "Created Successfully";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCompany(@PathVariable("id") String companyId)
    {
        companyService.deleteCompany(companyId);

        return "Deleted Successfully";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCompany(@RequestBody CompanyRequest companyRequest,
                                @PathVariable("id") String companyId)
    {
        companyService.updateCompany(companyRequest, companyId);

        return "Updated Successfully";
    }
}
