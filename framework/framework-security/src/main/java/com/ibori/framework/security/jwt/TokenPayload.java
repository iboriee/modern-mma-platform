package com.ibori.framework.security.jwt;

public record TokenPayload (
        String userId,
        String role,
        String type
) {}
