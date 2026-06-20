package com.ibori.mma.order.web;

import com.ibori.mma.order.domain.Order;
import com.ibori.mma.order.domain.OrderStatus;

public record OrderResponse(Long id, String productName, int quantity, OrderStatus status) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(order.getId(), order.getProductName(), order.getQuantity(), order.getStatus());
    }
}