package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.request.CreatePlanDto;
import com.fragile.ecommercebackend.request.InitializePaymentDto;
import com.fragile.ecommercebackend.response.CreatePlanResponse;
import com.fragile.ecommercebackend.response.InitializePaymentResponse;
import com.fragile.ecommercebackend.response.PaymentVerificationResponse;
import com.fragile.ecommercebackend.service.PaystackService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/paystack",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PaystackController {

    private final PaystackService paystackService;

    public PaystackController(PaystackService paystackService) {
        this.paystackService = paystackService;
    }

    @PostMapping("/createplan")
    public CreatePlanResponse createPlan(@Validated @RequestBody CreatePlanDto createPlanDto) throws Exception {
        return paystackService.createPlan(createPlanDto);
    }

    @PostMapping("/initializepayment")
    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentDto initializePaymentDto) throws Throwable {
        return paystackService.initializePayment(initializePaymentDto);
    }

    @GetMapping("/verifypayment/{reference}/{id}")
    public PaymentVerificationResponse paymentVerification(@PathVariable(value = "reference") String reference,
                                                           @PathVariable(value = "id") Long id) throws Exception {
        if (reference.isEmpty()) {
            throw new Exception("reference id must be provided in path");
        }
        return paystackService.paymentVerification(reference, id);
    }
}
