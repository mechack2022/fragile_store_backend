package com.fragile.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String orderId;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    private LocalDateTime orderDate;
    private LocalDateTime deliverDate;
    @OneToOne
    @JoinColumn(name="shipping_address_id")
    private Address shippingAddress;
    @Embedded
    private PaymentDetails paymentDetails= new PaymentDetails();
    private double totalPrice;
    @Column(name ="total_discounted_price")
    private Integer totalDiscountedPrice;
    private Integer discount;
    private String orderStatus;
    private Integer totalItem;
    private LocalDateTime createdAt;



}
