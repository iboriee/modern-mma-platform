package com.ibori.mma.order.application;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
public class OrderEventListener {

    private static final String TOPIC = "order.created";

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    public OrderEventListener(KafkaTemplate<Object, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handle(OrderCreatedEvent event) {
        log.info("[OrderCreatedEvent] 발행 시작. orderId: {}", event.orderId());
        kafkaTemplate.send(TOPIC, event.orderId().toString(), event);
    }
}