package com.fragile.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JsonIgnore
    private OrderEntity order;
    @ManyToOne
    private Product product;
    private Integer price;
    private Integer discountedPrice;
    private String size;
    private int quantity;
    private Long userId;
    private LocalDateTime createdAt;

}
