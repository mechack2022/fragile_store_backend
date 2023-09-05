package com.fragile.ecommercebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private Integer Price = 0;
    private int quantity;
    private int discountedPrice = 0;
    private Long userId;
    @ManyToOne
    private Product product;
    @JsonIgnore
    @ManyToOne
    private Cart cart;
}
