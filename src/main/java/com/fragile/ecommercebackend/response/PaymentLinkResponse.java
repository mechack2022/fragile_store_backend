package com.fragile.ecommercebackend.response;

import lombok.*;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;

}
