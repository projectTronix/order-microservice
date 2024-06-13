package com.mayank.ordermicroservice.repository;

import com.mayank.ordermicroservice.dto.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
    List<OrderItem> findAllByOrderId(Integer orderId);
}
