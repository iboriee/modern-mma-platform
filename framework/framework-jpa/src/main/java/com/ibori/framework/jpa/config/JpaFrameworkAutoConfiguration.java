package com.ibori.framework.jpa.config;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.util.Optional;

@AutoConfiguration
@ConditionalOnBean(DataSource.class)
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaFrameworkAutoConfiguration {

    /**
     * @CreatedBy, @LastModifiedBy 에 들어갈 '사용자 식별자'를 제공하는 빈(Bean).
     * todo: security 모듈 구현 시 토큰에서 가저오도록 수정, fallback 구조 고려
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> Optional.of("SYSTEM");
    }
}