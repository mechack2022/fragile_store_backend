package com.fragile.ecommercebackend.controller;

import com.fragile.ecommercebackend.model.User;
import com.fragile.ecommercebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
   private final UserService userService;

   @GetMapping("/profile")
   public ResponseEntity<User> userProfileHandler(@RequestHeader("Authorization") String jwt){
      return new ResponseEntity<>(userService.findUserProfileByJwt(jwt), HttpStatus.OK) ;
   }

}
