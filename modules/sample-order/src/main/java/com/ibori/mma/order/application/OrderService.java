package com.ibori.mma.order.application;

import com.ibori.framework.exception.BusinessException;
import com.ibori.mma.order.domain.Order;
import com.ibori.mma.order.domain.OrderRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public Order createOrder(String productName, int quantity) {
        Order order = new Order(productName, quantity);
        Order saved = orderRepository.save(order);

        eventPublisher.publishEvent(new OrderCreatedEvent(saved.getId(), productName, quantity));

        return saved;
    }

    @Cacheable(cacheNames = "order", key = "#orderId")
    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(OrderErrorCode.ORDER_NOT_FOUND));
    }
}