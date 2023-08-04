package com.fragile.ecommercebackend.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Embeddable
@Setter
@Getter
@RequiredArgsConstructor
public class Size {

    private String name;
    private int quantity;
}
