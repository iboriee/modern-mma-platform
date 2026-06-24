package com.ibori.framework.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "ibori.framework.security.jwt")
public class JwtProperties {

    private String secret;

    private Duration accessTokenTtl  = Duration.ofMinutes(30);
    private Duration refreshTokenTtl  = Duration.ofDays(14);

    private String issuer = "modern-mma-platform";

    public void setSecret(String secret) {
        if (secret == null || secret.isBlank()) {
            throw new IllegalArgumentException(
                    "ibori.framework.security.jwt.secret must not be blank...");
        }
        if (secret.getBytes().length < 32) {
            throw new IllegalArgumentException(
                    "ibori.framework.security.jwt.secret requires at least 32 bytes.");
        }
        this.secret = secret;
        this.secret = secret;
    }

    public void setAccessTokenTtl(Duration accessTokenTtl) {
        if (accessTokenTtl == null || accessTokenTtl.isNegative() || accessTokenTtl.isZero()) {
            throw new IllegalArgumentException("accessTokenTtl must be positive");
        }
        this.accessTokenTtl = accessTokenTtl;
    }

    public void setRefreshTokenTtl(Duration refreshTokenTtl) {
        if (refreshTokenTtl == null || refreshTokenTtl.isNegative() || refreshTokenTtl.isZero()) {
            throw new IllegalArgumentException("refreshTokenTtl must be positive");
        }
        this.refreshTokenTtl = refreshTokenTtl;
    }

    public String getSecret() { return secret; }
    public Duration getAccessTokenTtl() { return accessTokenTtl; }
    public Duration getRefreshTokenTtl() { return refreshTokenTtl; }
    public String getIssuer() { return issuer; }
    public void setIssuer(String issuer) { this.issuer = issuer; }

}