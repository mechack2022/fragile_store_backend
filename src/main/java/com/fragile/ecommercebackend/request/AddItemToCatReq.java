package com.fragile.ecommercebackend.request;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddItemToCatReq {
    private int quantity;
    private Integer price;
    private Long productId;
    private String size;
}
