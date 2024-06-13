package com.mayank.ordermicroservice.controller;

import com.mayank.ordermicroservice.config.UserService;
import com.mayank.ordermicroservice.dto.CartItem;
import com.mayank.ordermicroservice.dto.CustomResponse;
import com.mayank.ordermicroservice.dto.Order;
import com.mayank.ordermicroservice.dto.OrderItem;
import com.mayank.ordermicroservice.service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("orders")
@Validated
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;
    private final LogManager logManager = LogManager.getLogManager();
    private final Logger logger = logManager.getLogger(Logger.GLOBAL_LOGGER_NAME);
    @GetMapping("/view")
    public ResponseEntity<List<Order>> getOrders(@NotNull HttpServletRequest request) {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            List<Order> orders = orderService.getOrdersByUserEmail(userEmail);
            logger.log(Level.INFO, "Orders of user fetched Successfully.");
            return new ResponseEntity<>(orders, HttpStatus.OK);
        } catch (Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching orders of user - getOrders in OrderController " + e.getMessage());
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.OK);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<OrderItem>> getOrderById(@NotNull HttpServletRequest request, @PathVariable("id") Integer id) {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            Order order = orderService.getOrderByUserEmailAndId(userEmail, id);
            List<OrderItem> items = orderService.getOrderItemsByOrderId(order.getId());
            logger.log(Level.INFO, "Order fetched Successfully.");
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while fetching orders of user - getOrders in OrderController " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/add")
    public CustomResponse postOrder(@NotNull HttpServletRequest request, @RequestBody List<CartItem> items) {
        try {
            String userEmail = userService.extractEmailFromRequest(request);
            boolean status = orderService.saveOrder(userEmail, items);
            if(!status) throw new Exception("Error while saving order");
            logger.log(Level.INFO, "Order added Successfully.");
            return new CustomResponse("Order added Successfully.", HttpStatus.CREATED);
        } catch(Exception e) {
            logger.log(Level.WARNING, "Encountered a problem while adding order -- postProduct in OrderController. - " + e.getMessage());
            return new CustomResponse("Encountered a problem while adding order.", HttpStatus.BAD_REQUEST);
        }
    }
}
