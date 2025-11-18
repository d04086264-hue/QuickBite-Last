package com.cts.repository;

import com.cts.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface OrdersRepository extends JpaRepository<Order, Integer> {
    
    List<Order> findByCustomerOrderByIdDesc(Long customerId);
    List<Order> findByDeliveryPartnerOrderByIdDesc(Long deliveryPartnerId);
    List<Order> findAllByOrderByIdDesc();
   
}