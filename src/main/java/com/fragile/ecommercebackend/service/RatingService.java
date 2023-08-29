package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Rating;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.request.RatingRequest;

import java.util.List;

public interface RatingService {

    Rating createRating(RatingRequest ratingRequest, User user) throws ProductException;

    List<Rating> getProductRating(Long productId);

}
