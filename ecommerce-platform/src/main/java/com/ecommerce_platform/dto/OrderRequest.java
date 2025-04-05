package com.ecommerce_platform.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.ecommerce_platform.model.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderRequest {

    private BigDecimal totalPrice;
    private List<OrderItemRequest> items;
    private Payment paymentInfo;
}
