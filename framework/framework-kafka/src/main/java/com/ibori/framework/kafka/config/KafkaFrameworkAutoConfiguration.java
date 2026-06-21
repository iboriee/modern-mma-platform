package com.ibori.framework.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaConnectionDetails;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.LoggingProducerListener;
import org.springframework.kafka.support.converter.JsonMessageConverter;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.Map;

@Slf4j
@EnableKafka
@AutoConfiguration
@EnableConfigurationProperties(KafkaBackOffProperties.class)
public class KafkaFrameworkAutoConfiguration {

    private final KafkaBackOffProperties backOffProperties;

    public KafkaFrameworkAutoConfiguration(KafkaBackOffProperties backOffProperties) {
        this.backOffProperties = backOffProperties;
    }

    /**
     *  공통 JSON 메시지 컨버터
     */
    @Bean
    public JsonMessageConverter jsonMessageConverter() {
        return new JsonMessageConverter();
    }

    @Bean
    public ProducerFactory<Object, Object> producerFactory(
            KafkaProperties properties,
            KafkaConnectionDetails connectionDetails) {
        Map<String, Object> configs = properties.buildProducerProperties(null);
        configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, connectionDetails.getBootstrapServers());

        configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configs);
    }

    /**
     *  프로듀서 설정
     */
    @Bean
    public KafkaTemplate<Object, Object> kafkaTemplate(ProducerFactory<Object, Object> producerFactory) {
        KafkaTemplate<Object, Object> template = new KafkaTemplate<>(producerFactory);
        //template.setMessageConverter(jsonMessageConverter());
        template.setProducerListener(new LoggingProducerListener<>());
        template.setObservationEnabled(true);
        return template;
    }

    /**
     *  컨슈머 설정
     */
    @Bean
    public ConcurrentKafkaListenerContainerFactory<Object, Object> kafkaListenerContainerFactory(
            ConsumerFactory<Object, Object> consumerFactory,
            KafkaTemplate<Object, Object> kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setRecordMessageConverter(jsonMessageConverter());

        long retryAttempts = Math.max(0, backOffProperties.maxAttempts() - 1);
        FixedBackOff fixedBackOff = new FixedBackOff(backOffProperties.interval(), retryAttempts);

        // todo: .DLT 토픽 생성 확인
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);

        DefaultErrorHandler errorHandler = new DefaultErrorHandler(recoverer, fixedBackOff);
        errorHandler.setRetryListeners((record, ex, deliveryAttempt) ->
                log.warn("[Kafka Consumer Retry] key: {}, attempt: {}, error: {}",
                        record.key(), deliveryAttempt, ex.getMessage()));

        factory.setCommonErrorHandler(errorHandler);
        factory.getContainerProperties().setObservationEnabled(true);
        return factory;
    }
}