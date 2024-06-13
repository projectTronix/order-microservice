package com.mayank.ordermicroservice.service;

import com.mayank.ordermicroservice.dto.CartItem;
import com.mayank.ordermicroservice.dto.Order;
import com.mayank.ordermicroservice.dto.OrderItem;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getOrdersByUserEmail(String userEmail) throws Exception;

    Order getOrderByUserEmailAndId(String userEmail, Integer id) throws Exception;

    List<OrderItem> getOrderItemsByOrderId(Integer id) throws Exception;

    public boolean saveOrder(String userEmail, List<CartItem> cartItems);
}
