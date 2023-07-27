package com.commencis.customerservice;

import com.commencis.customerservice.model.Customer;
import com.commencis.customerservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
@EnableMongoAuditing
public class CustomerServiceApplication implements CommandLineRunner {

    @Autowired
    private final CustomerRepository customerRepository;
    public static void main(String[] args) {
        SpringApplication.run(CustomerServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        customerRepository.deleteAll();

        List<Customer> companies = Arrays.asList(
                new Customer("600f4997e3a11bc10091f786", "Kaan Uygur 1", "Istanbul"),
                new Customer("600f4997e3a11bc10091f723", "Kaan Uygur 2", "Istanbul"),
                new Customer("600f4997e3a1gfc10091f786", "Kaan Uygur 3", "Istanbul"),
                new Customer("600f4997e3a11go10091f786", "Kaan Uygur 4", "Istanbul"),
                new Customer("600f4997ea11bc10091f0286", "Kaan Uygur 5", "Istanbul")
        );

        customerRepository.saveAll(companies);
    }
}
