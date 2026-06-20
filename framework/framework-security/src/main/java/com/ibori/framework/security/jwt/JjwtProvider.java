package com.ibori.framework.security.jwt;

import com.ibori.framework.security.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class JjwtProvider implements JwtProvider {

    private final SecretKey secretKey;
    private final JwtProperties properties;

    public JjwtProvider(JwtProperties properties) {
        this.properties = properties;
        this.secretKey = Keys.hmacShaKeyFor(properties.getSecret().getBytes());
    }

    @Override
    public String createAccessToken(String userId, Map<String, Object> claims) {
        Instant now = Instant.now();
        Instant expiry = now.plus(properties.getAccessTokenTtl());

        return Jwts.builder()
                .subject(userId)
                .issuer(properties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String createRefreshToken(String userId) {
        Instant now = Instant.now();
        Instant expiry = now.plus(properties.getRefreshTokenTtl());

        return Jwts.builder()
                .subject(userId)
                .issuer(properties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiry))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public JwtClaims parse(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return new JwtClaims(
                claims.getSubject(),
                claims.getIssuedAt().toInstant(),
                claims.getExpiration().toInstant(),
                claims
        );
    }

    @Override
    public boolean isValid(String token) {
        try {
            parse(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}