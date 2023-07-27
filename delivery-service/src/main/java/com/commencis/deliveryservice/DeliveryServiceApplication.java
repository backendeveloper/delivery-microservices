package com.commencis.deliveryservice;

import com.commencis.deliveryservice.model.Delivery;
import com.commencis.deliveryservice.repository.DeliveryRepository;
import com.commencis.deliveryservice.repository.ResourceRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
@EnableMongoAuditing
@EnableMongoRepositories(repositoryBaseClass = ResourceRepositoryImpl.class)
public class DeliveryServiceApplication implements CommandLineRunner {

    @Autowired
    private final DeliveryRepository deliveryRepository;

    public static void main(String[] args) {
        SpringApplication.run(DeliveryServiceApplication.class, args);
    }

    @Override
    public void run(String... args) {
        if (deliveryRepository.count() < 1) {
            Delivery delivery = new Delivery();
            delivery.setName("Hizli Teslimat");
            delivery.setCompanyId("600f4997e3a11bc10091f786");
            delivery.setTrackingNumber("23123-12323");
            delivery.setToAddress("Istanbul");

            deliveryRepository.save(delivery);
        }
    }
}
