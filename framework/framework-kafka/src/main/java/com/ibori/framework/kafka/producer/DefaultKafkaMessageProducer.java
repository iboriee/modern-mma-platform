package com.ibori.framework.kafka.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

@Slf4j
@RequiredArgsConstructor
public class DefaultKafkaMessageProducer implements MessageProducer {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Override
    public void send(String topic, Object payload) {
        log.debug("외부 메시지 발송 (Kafka): topic={}", topic);
        kafkaTemplate.send(topic, payload);
    }

    @Override
    public void send(String topic, String key, Object payload) {
        log.debug("외부 메시지 발송 (Kafka): topic={}, key={}", topic, key);
        kafkaTemplate.send(topic, key, payload);
    }
}