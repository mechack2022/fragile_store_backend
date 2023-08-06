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
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String size;
    private Integer Price;
    private int quantity;
    private Integer discountedPrice;
    private Long userId;
    @ManyToOne
    private Product product;
    @JsonIgnore
    @ManyToOne
    private Cart cart;
}
