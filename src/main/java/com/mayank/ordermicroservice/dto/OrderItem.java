package com.mayank.ordermicroservice.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Valid
@Table(name = "orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(nullable = false)
    @NotNull(message = "Product Id must not be Null.")
    @NotBlank(message = "Product Id must not be blank.")
    private String productId;
    @Positive(message = "Quantity must be greater than zero.")
    @NotNull(message = "Quantity must not be null.")
    @Column(nullable = false)
    private Integer quantity;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id", referencedColumnName="order_id")
    @JsonBackReference
    private Order order;
    public OrderItem(String productId, Integer quantity, Order order) {
        this.productId = productId;
        this.quantity = quantity;
        this.order = order;
    }
}
