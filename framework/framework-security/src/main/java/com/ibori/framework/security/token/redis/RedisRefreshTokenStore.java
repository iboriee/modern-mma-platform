package com.ibori.framework.security.token.redis;

import com.ibori.framework.security.token.RefreshTokenStore;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.Optional;

public class RedisRefreshTokenStore implements RefreshTokenStore {

    private static final String KEY_PREFIX = "refresh:";

    private final StringRedisTemplate redisTemplate;

    public RedisRefreshTokenStore(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(String userId, String deviceId, String refreshTokenHash, Duration ttl) {
        String key = KEY_PREFIX + userId;
        redisTemplate.opsForHash().put(key, deviceId, refreshTokenHash);
        redisTemplate.expire(key, ttl);
    }

    @Override
    public Optional<String> find(String userId, String deviceId) {
        return Optional.ofNullable(redisTemplate.opsForValue().get(key(userId, deviceId)));
    }

    @Override
    public void delete(String userId, String deviceId) {
        redisTemplate.delete(key(userId, deviceId));
    }

    /**
     * TODO: KEYS 명령은 운영 규모(사용자/디바이스 수 증가) 커지면 Redis를 블로킹시킬 수 있음.
     *       SCAN(cursor 기반) 방식으로 교체 검토 필요.
     */
    @Override
    public void deleteAll(String userId) {
        redisTemplate.delete(KEY_PREFIX + userId);
    }

    private String key(String userId, String deviceId) {
        return KEY_PREFIX + userId + ":" + deviceId;
    }
}