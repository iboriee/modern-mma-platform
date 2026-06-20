package com.ibori.framework.kafka.producer;

/**
 * 도메인(비즈니스) 계층에서 외부 시스템으로 메시지를 발행할 때 사용하는 인터페이스.
 */
public interface MessageProducer {

    /**
     * @param topic 발행할 토픽 (또는 큐 이름)
     * @param payload 전송할 데이터 (DTO)
     */
    void send(String topic, Object payload);

    /**
     * @param topic 발행할 토픽
     * @param key 메시지 키 (파티션 분배용)
     * @param payload 전송할 데이터
     */
    void send(String topic, String key, Object payload);
}