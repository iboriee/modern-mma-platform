package com.ibori.framework.security.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

public interface SecurityFilterChainExtension {

    default String[] publicPatterns() {
        return new String[0];
    }

    default void configure(HttpSecurity httpSecurity) throws Exception {
    }
}