package com.fragile.ecommercebackend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String description;

    private int price;

    private int discountedPrice;

    private int discountedPercent;

    private int quantity;
    private String brand;

    private String color;

    private String imageUrl;
//    @Embedded
    @ElementCollection
    @Column(name = "sizes")
    private Set<Size> sizes = new HashSet<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)

    private List<Rating> ratings = new ArrayList<>();

    @OneToMany(mappedBy = "product", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Review> reviews= new ArrayList<>();

    private int numRatings;

    @JoinColumn(name="category_id")
    @ManyToOne
    private Category category;

    private LocalDateTime createdAt;

}
