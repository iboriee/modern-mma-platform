package com.ibori.framework.security.jwt;

public interface JwtProvider {

    String createAccessToken(String userId, java.util.Map<String, Object> claims);

    String createRefreshToken(String userId);

    JwtClaims parse(String token);

    boolean isValid(String token);
}