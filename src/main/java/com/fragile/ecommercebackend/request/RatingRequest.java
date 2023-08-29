package com.fragile.ecommercebackend.request;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RatingRequest {

    private Long productId;
    private Double rating;
}
