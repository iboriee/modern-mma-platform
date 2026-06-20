package com.ibori.framework.response;

public record ValidationError(
        String field,
        String value,
        String reason
) {}
