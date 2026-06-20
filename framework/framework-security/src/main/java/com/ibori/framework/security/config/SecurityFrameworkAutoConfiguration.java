package com.ibori.framework.security.config;

import com.ibori.framework.security.facade.DefaultTokenFacade;
import com.ibori.framework.security.facade.TokenFacade;
import com.ibori.framework.security.jwt.JjwtProvider;
import com.ibori.framework.security.jwt.JwtProvider;
import com.ibori.framework.security.token.RefreshTokenStore;
import com.ibori.framework.security.token.Sha256TokenHasher;
import com.ibori.framework.security.token.TokenHasher;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@EnableWebSecurity
@AutoConfiguration
@EnableConfigurationProperties(JwtProperties.class)
public class SecurityFrameworkAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public JwtProvider jwtProvider(JwtProperties jwtProperties) {
        return new JjwtProvider(jwtProperties);
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenHasher tokenHasher() {
        return new Sha256TokenHasher();
    }

    @Bean
    @ConditionalOnMissingBean
    public TokenFacade tokenFacade(JwtProvider jwtProvider,
                                   RefreshTokenStore refreshTokenStore,
                                   TokenHasher tokenHasher,
                                   JwtProperties jwtProperties) {
        return new DefaultTokenFacade(
                jwtProvider,
                refreshTokenStore,
                tokenHasher,
                jwtProperties.getRefreshTokenTtl()
        );
    }
}