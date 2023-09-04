package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Review;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.request.ReviewRequest;
import com.fragile.ecommercebackend.service.ReviewService;
import com.fragile.ecommercebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/reviews")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Review> createRating(@RequestHeader("Authorization") String jwt, @RequestBody ReviewRequest request)
            throws UserException, ProductException {
        User user = userService.findUserProfileByJwt(jwt);
        Review createdReview = reviewService.createReview(request, user);

        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Review>> getProductRating(@RequestHeader("Authorization") String jwt, @PathVariable("productId") Long productId)
            throws UserException, ProductException
    {
        User user = userService.findUserProfileByJwt(jwt);
        List<Review> productReviews =  reviewService.getReviewByProductId(productId);
        return new ResponseEntity<>(productReviews, HttpStatus.OK);
    }

}
