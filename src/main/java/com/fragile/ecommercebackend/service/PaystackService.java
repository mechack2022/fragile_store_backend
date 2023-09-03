package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.request.CreatePlanDto;
import com.fragile.ecommercebackend.request.InitializePaymentDto;
import com.fragile.ecommercebackend.response.CreatePlanResponse;
import com.fragile.ecommercebackend.response.InitializePaymentResponse;
import com.fragile.ecommercebackend.response.PaymentVerificationResponse;

public interface PaystackService {
    CreatePlanResponse createPlan(CreatePlanDto createPlanDto) throws Exception;
    InitializePaymentResponse initializePayment(InitializePaymentDto initializePaymentDto);
    PaymentVerificationResponse paymentVerification(String reference, String plan, Long id) throws Exception;

}
