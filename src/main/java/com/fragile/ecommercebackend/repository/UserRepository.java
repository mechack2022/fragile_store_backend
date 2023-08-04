package com.fragile.ecommercebackend.repository;


import com.fragile.ecommercebackend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
   User findByEmail(String email);
}
