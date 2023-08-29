package com.fragile.ecommercebackend.repository;

import com.fragile.ecommercebackend.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {

}
