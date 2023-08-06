package com.fragile.ecommercebackend.request;

import com.fragile.ecommercebackend.model.Size;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateProductRequest {

    private String title;
    private String description;
    private String brand;
    private int price;
    private int discountedPrice;
    private int discountedPercent;
    private String color;
    private int quantity;
    private Set<Size>  sizes= new HashSet<>();
    private  String imageUrl;
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;

}

