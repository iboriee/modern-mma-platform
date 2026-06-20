package com.ibori.framework.security.facade;

import com.ibori.framework.security.jwt.JwtProvider;
import com.ibori.framework.security.token.*;

import java.time.Duration;
import java.util.Map;

public class DefaultTokenFacade implements TokenFacade {

    private final JwtProvider jwtProvider;
    private final RefreshTokenStore refreshTokenStore;
    private final TokenHasher tokenHasher;       // refresh token 해싱 책임 분리
    private final Duration refreshTtl;

    public DefaultTokenFacade(JwtProvider jwtProvider,
                              RefreshTokenStore refreshTokenStore,
                              TokenHasher tokenHasher,
                              Duration refreshTtl) {
        this.jwtProvider = jwtProvider;
        this.refreshTokenStore = refreshTokenStore;
        this.tokenHasher = tokenHasher;
        this.refreshTtl = refreshTtl;
    }

    @Override
    public TokenPair issueTokens(String userId, String deviceId, Map<String, Object> claims) {
        String accessToken = jwtProvider.createAccessToken(userId, claims);
        String refreshToken = jwtProvider.createRefreshToken(userId);

        refreshTokenStore.save(userId, deviceId, tokenHasher.hash(refreshToken), refreshTtl);

        return new TokenPair(accessToken, refreshToken);
    }

    @Override
    public TokenRotationResult reissueTokens(String refreshToken, String deviceId) {
        if (!jwtProvider.isValid(refreshToken)) {
            return new TokenRotationResult.Invalid();
        }

        var claims = jwtProvider.parse(refreshToken);
        String userId = claims.userId();
        String incomingHash = tokenHasher.hash(refreshToken);

        var stored = refreshTokenStore.find(userId, deviceId);

        if (stored.isEmpty() || !stored.get().equals(incomingHash)) {
            return new TokenRotationResult.ReuseDetected(userId);
        }

        TokenPair newPair = issueTokens(userId, deviceId, Map.of());
        return new TokenRotationResult.Success(newPair);
    }

    @Override
    public void revoke(String userId, String deviceId) {
        refreshTokenStore.delete(userId, deviceId);
    }

    @Override
    public void revokeAll(String userId) {
        refreshTokenStore.deleteAll(userId);
    }
}