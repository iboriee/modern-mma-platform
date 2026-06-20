package com.ibori.mma.order.application;

public record OrderCreatedEvent(
        Long orderId,
        String productName,
        int quantity)
{}