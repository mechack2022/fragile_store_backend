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
    private int discountPercent;
    private String color;
    private int quantity;
    private Set<Size> size = new HashSet<>();
    private  String imageUrl;

//    NOTE THIS CATEGORIES LOOK SOMETHING LIKE
//            men/clothing/men_shirt
//    so, topLevelCategory= men, secondLevelCategory = clothing, thirdLevelCategory= men_shirt
    private String topLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;

}

