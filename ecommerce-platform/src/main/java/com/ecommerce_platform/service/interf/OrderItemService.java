package com.ecommerce_platform.service.interf;

import com.ecommerce_platform.dto.OrderRequest;
import com.ecommerce_platform.dto.Response;
import com.ecommerce_platform.enums.OrderStatus;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface OrderItemService {
    Response placeOrder(OrderRequest orderRequest);
    Response updateOrderItemStatus(Long orderItemId, String status);
    Response filterOrderItems(OrderStatus status, LocalDateTime startDate, LocalDateTime endDate, Long itemId, Pageable pageable);
}
