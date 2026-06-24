package com.ibori.framework.security.jwt;

import java.util.Map;

public interface JwtProvider {

    String createAccessToken(String userId, Map<String, Object> claims);

    String createRefreshToken(String userId);

    JwtClaims parse(String token);

    boolean isValid(String token);
}