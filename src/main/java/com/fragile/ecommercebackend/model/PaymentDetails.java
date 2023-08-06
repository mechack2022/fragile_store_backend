package com.fragile.ecommercebackend.model;

import jakarta.persistence.Embeddable;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class PaymentDetails {

    private String paymentStatus;
    private String paymentMethod;
    private String paymentId;
    private String razorpayPaymentLinkId;
    private String razorpayPaymentLinkReference;
    private String razorpayPaymentLinkStatus;
    private String razorpayPaymentId;
}
