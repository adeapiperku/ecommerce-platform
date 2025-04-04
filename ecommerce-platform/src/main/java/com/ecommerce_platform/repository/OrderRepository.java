package com.ecommerce_platform.repository;

import com.ecommerce_platform.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
