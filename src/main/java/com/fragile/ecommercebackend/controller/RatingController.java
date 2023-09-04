package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.Rating;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.request.RatingRequest;
import com.fragile.ecommercebackend.response.ApiResponse;
import com.fragile.ecommercebackend.service.RatingService;
import com.fragile.ecommercebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/ratings")
@RestController
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Rating> createRating(@RequestHeader("Authorization") String jwt, @RequestBody RatingRequest request)
     throws UserException, ProductException {
           User user = userService.findUserProfileByJwt(jwt);
           Rating createdRating = ratingService.createRating(request, user);

           return new ResponseEntity<>(createdRating, HttpStatus.CREATED);

    }
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Rating>> getProductRating(@RequestHeader("Authorization") String jwt, @PathVariable("productId") Long productId)
            throws UserException, ProductException
    {
        User user = userService.findUserProfileByJwt(jwt);
        List<Rating> ratings =  ratingService.getProductRating(productId);
        return new ResponseEntity<>(ratings, HttpStatus.OK);
    }

}