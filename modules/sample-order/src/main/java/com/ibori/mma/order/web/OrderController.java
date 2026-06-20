package com.ibori.mma.order.web;

import com.ibori.framework.response.ApiResponse;
import com.ibori.mma.order.application.OrderService;
import com.ibori.mma.order.domain.Order;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ApiResponse<OrderResponse> create(@Valid @RequestBody OrderCreateRequest request) {
        Order order = orderService.createOrder(request.productName(), request.quantity());
        return ApiResponse.success(OrderResponse.from(order));
    }

    @GetMapping("/{id}")
    public ApiResponse<OrderResponse> get(@PathVariable Long id) {
        Order order = orderService.getOrder(id);
        return ApiResponse.success(OrderResponse.from(order));
    }
}