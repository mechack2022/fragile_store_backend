package com.fragile.ecommercebackend.controller;


import com.fragile.ecommercebackend.model.OrderEntity;
import com.fragile.ecommercebackend.response.PaymentLinkResponse;
import com.fragile.ecommercebackend.service.OrderService;
import com.fragile.ecommercebackend.service.UserService;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Value("razorpay.api.secret")
    String apiSecret;

    @Value("razorpay.api.key")
    String apiKey;

    private final UserService userService;

    private final OrderService orderService;

@PostMapping("/{orderId}")
    public ResponseEntity<PaymentLinkResponse> createPayymentLink(@PathVariable("orderId") Long orderId, @RequestHeader("Authorization") String jwt){

    OrderEntity order = orderService.findOrderById(orderId);
    try{
        RazorpayClient newClient = new RazorpayClient(apiKey, apiSecret);
        JSONObject paymentLink = new JSONObject();



    }catch (Exception ex){
//        TODO HANDLEXCEPTIPN
    }

    return null;
}



}
