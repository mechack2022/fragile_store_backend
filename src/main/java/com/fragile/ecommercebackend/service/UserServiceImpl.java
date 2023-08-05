package com.fragile.ecommercebackend.service;

import com.fragile.ecommercebackend.exceptions.UserException;
import com.fragile.ecommercebackend.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public User findUserById(Long userId) throws UserException {
        return null;
    }

    @Override
    public User findUserProfileByJwt(String jwt) throws UserException {
        return null;
    }
}
