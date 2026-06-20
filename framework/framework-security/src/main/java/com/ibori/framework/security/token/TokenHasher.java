package com.ibori.framework.security.token;

public interface TokenHasher {
    String hash(String rawToken);
}