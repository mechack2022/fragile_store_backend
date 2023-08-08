package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.ProductException;
import com.fragile.ecommercebackend.model.Review;
import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.request.ReviewRequest;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewRequest request, User user) throws ProductException;

    List<Review> getReviewByProductId(Long productId);
}
