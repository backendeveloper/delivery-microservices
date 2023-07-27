package com.commencis.companyservice;

import com.commencis.companyservice.model.Company;
import com.commencis.companyservice.repository.CompanyRepository;
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
public class CompanyServiceApplication implements CommandLineRunner {

    @Autowired
    private final CompanyRepository companyRepository;

    public static void main(String[] args) {
        SpringApplication.run(CompanyServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        companyRepository.deleteAll();

        List<Company> companies = Arrays.asList(
                new Company("600f4997e3a11bc10091f786", "Commencis co 1", "Commencis smart solution", "Istanbul"),
                new Company("600f49a1a5bd0e51ceb6c2d3", "Commencis co 2", "Commencis smart solution", "Istanbul"),
                new Company("600f49b6ff49b4e466efb4c4", "Commencis co 3", "Commencis smart solution", "Istanbul"),
                new Company("600f49c16c3c8f51ff49d30e", "Commencis co 4", "Commencis smart solution", "Istanbul"),
                new Company("600f49ddc441d3b63d2f3f15", "Commencis co 5", "Commencis smart solution", "Istanbul")
        );

        companyRepository.saveAll(companies);
    }
}
