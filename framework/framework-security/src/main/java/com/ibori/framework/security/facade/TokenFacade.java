package com.ibori.framework.security.facade;

import com.ibori.framework.security.token.TokenPair;
import com.ibori.framework.security.token.TokenRotationResult;

public interface TokenFacade {

    TokenPair issueTokens(String userId, String deviceId, java.util.Map<String, Object> claims);

    TokenRotationResult reissueTokens(String refreshToken, String deviceId);

    void revoke(String userId, String deviceId);

    void revokeAll(String userId);
}