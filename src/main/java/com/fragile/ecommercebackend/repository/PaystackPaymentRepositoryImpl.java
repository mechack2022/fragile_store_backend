package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.PaymentPaystack;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaystackPaymentRepositoryImpl extends JpaRepository<PaymentPaystack, Long> {
}
