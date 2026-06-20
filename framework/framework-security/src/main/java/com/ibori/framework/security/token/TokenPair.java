package com.ibori.framework.security.token;

public record TokenPair(
        String accessToken,
        String refreshToken
) {}