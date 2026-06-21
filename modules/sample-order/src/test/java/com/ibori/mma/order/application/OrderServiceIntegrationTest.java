package com.ibori.mma.order.application;

import com.ibori.framework.test.support.KafkaContainerSupport;
import com.ibori.framework.test.support.PostgresContainerSupport;
import com.ibori.framework.test.support.RedisContainerSupport;
import com.ibori.mma.order.OrderTestApplication;
import com.ibori.mma.order.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;
import java.util.stream.StreamSupport;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = OrderTestApplication.class)
@ActiveProfiles("test")
class OrderServiceIntegrationTest implements PostgresContainerSupport, RedisContainerSupport, KafkaContainerSupport {

    @Autowired
    OrderService orderService;

    @Autowired
    CacheManager cacheManager;

    @Test
    void 주문을_생성하면_저장되고_커밋후_이벤트가_카프카로_발행된다() {
        // given
        Consumer<String, OrderCreatedEvent> consumer = createTestConsumer();
        consumer.subscribe(java.util.List.of("order.created"));

        // when
        Order saved = orderService.createOrder("키보드", 2);

        // then: DB 저장 확인
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getProductName()).isEqualTo("키보드");

        // then: 카프카 메시지 도착 확인 (AFTER_COMMIT 이후 비동기 발행이라 폴링 필요)
        ConsumerRecords<String, OrderCreatedEvent> records =
                KafkaTestUtils.getRecords(consumer, java.time.Duration.ofSeconds(10));

        boolean found = StreamSupport.stream(records.records("order.created").spliterator(), false)
                .anyMatch(r -> r.value().orderId().equals(saved.getId()));

        assertThat(found).isTrue();

        consumer.close();
    }

    @Test
    void 같은_주문을_두번_조회하면_두번째는_캐시에서_나온다() {
        // given
        Order saved = orderService.createOrder("마우스", 1);

        // when
        Order first = orderService.getOrder(saved.getId());
        Order second = orderService.getOrder(saved.getId());

        // then: 캐시에 값이 들어가 있는지 확인
        var cache = cacheManager.getCache("order");
        assertThat(cache).isNotNull();
        assertThat(cache.get(saved.getId())).isNotNull();

        assertThat(first.getId()).isEqualTo(second.getId());
    }

    private Consumer<String, OrderCreatedEvent> createTestConsumer() {
        Map<String, Object> props = KafkaTestUtils.consumerProps(
                KafkaContainerSupport.KAFKA.getBootstrapServers(),"test-group", "true" );

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        var factory = new org.springframework.kafka.core.DefaultKafkaConsumerFactory<String, OrderCreatedEvent>(props);
        return factory.createConsumer();
    }
}