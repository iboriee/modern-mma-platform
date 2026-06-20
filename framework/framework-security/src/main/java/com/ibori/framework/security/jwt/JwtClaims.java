package com.ibori.framework.security.jwt;

import java.time.Instant;
import java.util.Map;

public record JwtClaims(
        String userId,
        Instant issuedAt,
        Instant expiresAt,
        Map<String, Object> customClaims
) {}