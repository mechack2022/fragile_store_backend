package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.OrderException;
import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.repository.OrderRepository;
import com.fragile.ecommercebackend.request.CreatePlanDto;
import com.fragile.ecommercebackend.request.InitializePaymentDto;
import com.fragile.ecommercebackend.response.ApiResponse;
import com.fragile.ecommercebackend.response.CreatePlanResponse;
import com.fragile.ecommercebackend.response.InitializePaymentResponse;
import com.fragile.ecommercebackend.response.PaymentVerificationResponse;
import com.fragile.ecommercebackend.service.OrderService;
import com.fragile.ecommercebackend.service.PaystackService;
import com.fragile.ecommercebackend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping(
        value = "/paystack",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class PaystackController {

    private final PaystackService paystackService;
    private final OrderRepository orderRepository;

    private final OrderService orderService;

    private final UserService userService;

    public PaystackController(PaystackService paystackService,
                              OrderRepository orderRepository, OrderService orderService, UserService userService) {
        this.paystackService = paystackService;
        this.orderRepository = orderRepository;
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("/createplan")
    public CreatePlanResponse createPlan(@Validated @RequestBody CreatePlanDto createPlanDto) throws Exception {
        return paystackService.createPlan(createPlanDto);
    }

//    @PostMapping("/initializepayment")
//    public InitializePaymentResponse initializePayment(@Validated @RequestBody InitializePaymentDto initializePaymentDto) throws Throwable {
//        return paystackService.initializePayment(initializePaymentDto);
//    }

    @PostMapping("/payment/{orderId}")
    public ResponseEntity<InitializePaymentResponse> createPayment(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Throwable, UsernameNotFoundException, OrderException {
        User user = userService.findUserProfileByJwt(jwt);
        OrderEntity order = orderService.findOrderById(orderId);
        InitializePaymentDto initializePaymentDto = new InitializePaymentDto();
        initializePaymentDto.setEmail(user.getEmail());
        double amount = order.getTotalPrice();
        String amountString = Double.toString(amount);
        initializePaymentDto.setAmount(amountString);
         return new ResponseEntity<>(paystackService.initializePayment(initializePaymentDto), HttpStatus.CREATED);
    }


    @GetMapping("/payments")
    public ResponseEntity<ApiResponse> redirect(@RequestParam(value = "reference") String reference, @RequestParam("orderId") Long orderId ) throws Exception{
         if (reference.isEmpty()) {
            throw new Exception("reference id must be provided in path");
        }
         PaymentVerificationResponse  paymentVerificationResponse = paystackService.paymentVerification(reference, orderId);
         log.info("payment Verifiication response : {}", paymentVerificationResponse );
         ApiResponse apiResponse = new ApiResponse();
         apiResponse.setMessage("Your order was placed successfully");
         apiResponse.setStatus(true);
        return new ResponseEntity<>( apiResponse, HttpStatus.OK );
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
