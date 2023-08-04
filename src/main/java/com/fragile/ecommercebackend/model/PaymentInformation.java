package com.fragile.ecommercebackend.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Embeddable
@Setter
@Getter
@RequiredArgsConstructor
public class PaymentInformation {

    private String cardholderName;

    private LocalDate expirationDate;

    private String cardNumber;

    private String cvv;
}
