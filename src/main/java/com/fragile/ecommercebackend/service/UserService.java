package com.fragile.ecommercebackend.service;


import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.User;

public interface UserService {

    User findUserById(Long userId) throws UserException;

    User findUserProfileByJwt(String jwt) throws UserException;
}
