package com.commencis.customerservice.service;

import com.commencis.customerservice.dto.CustomerRequest;
import com.commencis.customerservice.dto.CustomerResponse;
import com.commencis.customerservice.model.Customer;
import com.commencis.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;

    public List<CustomerResponse> getCustomers() {
        List<Customer> customers = customerRepository.findAll();

        return customers.stream().map(this::mapToCustomerResponse).toList();
    }

    public CustomerResponse getCustomer(String customerId) {
        Customer customer = customerRepository.findById(customerId).get();

        return mapToCustomerResponse(customer);
    }

    public void createCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.builder()
                .name(customerRequest.getName())
                .address(customerRequest.getAddress())
                .build();

        customerRepository.save(customer);
        log.info("Customer {} is saved", customer.getId());
    }

    public void updateCustomer(CustomerRequest customerRequest, String customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.isPresent()) {
            Customer customerData = customer.get();
            customerData.setName(customerRequest.getName());
            customerData.setAddress(customerRequest.getAddress());

            customerRepository.save(customerData);
        }

        log.info("Customer {} is updated", customerId);
    }

    public void deleteCustomer(String customerId) {
        customerRepository.deleteById(customerId);
    }

    private CustomerResponse mapToCustomerResponse(Customer customer) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .address(customer.getAddress())
                .build();
    }
}
