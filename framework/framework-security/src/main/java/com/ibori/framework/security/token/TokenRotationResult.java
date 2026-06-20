package com.ibori.framework.security.token;

public sealed interface TokenRotationResult {

    record Success(TokenPair tokenPair) implements TokenRotationResult {}

    /**
     * 이미 폐기된 Refresh Token이 재사용 시도된 경우.
     * 해당 사용자의 모든 토큰을 강제 무효화.
     */
    record ReuseDetected(String userId) implements TokenRotationResult {}

    record Invalid() implements TokenRotationResult {}
}