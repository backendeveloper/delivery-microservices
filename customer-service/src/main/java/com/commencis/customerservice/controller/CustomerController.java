package com.commencis.customerservice.controller;

import com.commencis.customerservice.dto.CustomerRequest;
import com.commencis.customerservice.dto.CustomerResponse;
import com.commencis.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<CustomerResponse> getCustomers() {
        return customerService.getCustomers();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CustomerResponse getCustomer(@PathVariable("id") String customerId)
    {
        return customerService.getCustomer(customerId);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public String createCustomer(@RequestBody CustomerRequest customerRequest) {
        customerService.createCustomer(customerRequest);

        return "Created Successfully";
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String updateCustomer(@RequestBody CustomerRequest customerRequest,
                                @PathVariable("id") String customerId)
    {
        customerService.updateCustomer(customerRequest, customerId);

        return "Updated Successfully";
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String deleteCustomer(@PathVariable("id") String customerId)
    {
        customerService.deleteCustomer(customerId);

        return "Deleted Successfully";
    }
}
