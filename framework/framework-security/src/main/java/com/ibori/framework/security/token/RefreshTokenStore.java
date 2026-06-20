package com.ibori.framework.security.token;

import java.util.Optional;

public interface RefreshTokenStore {

    void save(String userId, String deviceId, String refreshTokenHash, java.time.Duration ttl);
    Optional<String> find(String userId, String deviceId);
    void delete(String userId, String deviceId);
    void deleteAll(String userId);

}