package com.mayank.ordermicroservice.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@Valid
@Entity
@Table(name = "order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer id;
    @Column(nullable = false)
    @NotNull(message = "User Email must not be Null.")
    @NotBlank(message = "User Email must not be blank.")
    private String userEmail;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @JsonManagedReference
    private List<OrderItem> items;
    private String timeStamp;

    public Order() {
        this.timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
    }
}
