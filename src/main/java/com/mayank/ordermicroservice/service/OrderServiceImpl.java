package com.mayank.ordermicroservice.service;

import com.mayank.ordermicroservice.dto.CartItem;
import com.mayank.ordermicroservice.dto.Order;
import com.mayank.ordermicroservice.dto.OrderItem;
import com.mayank.ordermicroservice.repository.OrderItemRepository;
import com.mayank.ordermicroservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@Transactional
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    LogManager logManager = LogManager.getLogManager();
    Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @Override
    public List<Order> getOrdersByUserEmail(String userEmail) throws Exception {
        try {
            List<Order> orders = orderRepository.findAllByUserEmail(userEmail);
            if(orders.isEmpty()) {
                throw new Exception("No orders found.");
            }
            return orders;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching orders -- getOrdersByUserEmail in OrderService. - " + e.getMessage());
            throw new Exception("Error while fetching Orders of user.");
        }
    }

    @Override
    public Order getOrderByUserEmailAndId(String userEmail, Integer orderId) throws Exception {
        try {
            Optional<Order> opt = orderRepository.findByUserEmailAndId(userEmail, orderId);
            if(opt.isEmpty()) {
                throw new Exception("Order not found");
            }
            return opt.get();
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching cart by user email -- getCartByUserEmail in OrderService. - " + e.getMessage());
            throw new Exception("Error while fetching Order of user.");
        }
    }

    @Override
    public List<OrderItem> getOrderItemsByOrderId(Integer orderId) throws Exception {
        try {
            List<OrderItem> items = orderItemRepository.findAllByOrderId(orderId);
            if(items.isEmpty()) {
                throw new Exception("No items in order found.");
            }
            logger.log(Level.INFO, "Order items found in order id " + orderId);
            return items;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching order items -- getOrderItemsByOrderId in OrderService. - " + e.getMessage());
            throw new Exception("Error while fetching Order of user.");
        }
    }

    @Override
    public boolean saveOrder(String userEmail, List<CartItem> cartItems) {
        try {
            Order order = cartToOrderMapper(userEmail, cartItems);
            orderRepository.save(order);
            logger.log(Level.INFO, "Order saved successfully");
            return true;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while saving order -- saveOrder in OrderService. - " + e.getMessage());
            return false;
        }
    }

    private Order cartToOrderMapper(String userEmail, List<CartItem> cartItems) throws Exception {
        try {
            Order order = new Order();
            order.setUserEmail(userEmail);
            order.setItems(cartItemToOrderItemMapper(cartItems, order));
            logger.log(Level.INFO, "Cart to Order Mapped successfully");
            return order;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while mapping cart to order -- cartToOrderMapper in OrderService. - " + e.getMessage());
            throw new Exception("Error while mapping cart to order.");
        }
    }

    private List<OrderItem> cartItemToOrderItemMapper(List<CartItem> items, Order order) throws Exception {
        try {
            List<OrderItem> orderItems = new ArrayList<>();
            for(CartItem item : items) {
                orderItems.add(new OrderItem(item.getProductId(), item.getQuantity(), order));
            }
            logger.log(Level.INFO, "CartItems to OrderItems Mapped successfully");
            return orderItems;
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while mapping cartItems to orderItems -- cartItemToOrderItemMapper in OrderService. - " + e.getMessage());
            throw new Exception("Error while mapping cartItems to orderItems.");
        }
    }
}
