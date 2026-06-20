package com.ibori.framework.security.token.redis;

import com.ibori.framework.security.token.RefreshTokenStore;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.ibori.framework.security.config.SecurityFrameworkAutoConfiguration;

@AutoConfiguration(before = SecurityFrameworkAutoConfiguration.class)
@ConditionalOnClass(StringRedisTemplate.class)
public class RedisTokenStoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public RefreshTokenStore refreshTokenStore(StringRedisTemplate redisTemplate) {
        return new RedisRefreshTokenStore(redisTemplate);
    }
}