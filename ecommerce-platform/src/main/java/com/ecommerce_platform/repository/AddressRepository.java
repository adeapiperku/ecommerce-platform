package com.ecommerce_platform.repository;

import com.ecommerce_platform.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
