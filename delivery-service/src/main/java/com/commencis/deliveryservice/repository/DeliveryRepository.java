package com.commencis.deliveryservice.repository;

import com.commencis.deliveryservice.model.Delivery;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeliveryRepository extends ResourceRepository<Delivery, String> {
    List<Delivery> findByTrackingNumber(String trackingNumber);
}

//@Repository
//public interface DeliveryRepository<T, ID> extends CrudRepository<T, ID> {
//    Page<T> findAll(Pageable pageable);
//}

//@Repository
//public interface DeliveryRepository extends MongoRepository<Delivery, String> {
////    Page<Delivery> findByPublished(boolean published, Pageable pageable);
//
//    List<Delivery> findByTrackingNumber(String trackingNumber);
//
//    Page<Delivery> findByNameContainingIgnoreCase(String name, Pageable pageable);
//}
