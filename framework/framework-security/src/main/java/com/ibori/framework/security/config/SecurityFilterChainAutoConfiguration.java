package com.ibori.framework.security.config;

import com.ibori.framework.security.filter.JwtAuthenticationFilter;
import com.ibori.framework.security.jwt.JwtProvider;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@AutoConfiguration(before = SecurityFilterAutoConfiguration.class)
public class SecurityFilterChainAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            JwtProvider jwtProvider,
            List<SecurityFilterChainExtension> extensions) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptions -> exceptions
                        .authenticationEntryPoint((request, response, authException) -> {
                            // 401 Unauthorized 공통 응답 처리
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("""
                                {
                                    "success": false,
                                    "code": "COMMON-401",
                                    "message": "인증이 필요합니다."
                                }
                                """);
                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            // 403 Forbidden 공통 응답 처리
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                            response.setContentType("application/json;charset=UTF-8");
                            response.getWriter().write("""
                                {
                                    "success": false,
                                    "code": "COMMON-403",
                                    "success": "접근 권한이 없습니다."
                                }
                                """);
                        })
                )
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/actuator/health", "/error").permitAll();
                    for(SecurityFilterChainExtension extension : extensions) {
                        auth.requestMatchers(extension.publicPatterns()).permitAll();
                    }
                    auth.anyRequest().authenticated();

                })
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class);

            for(SecurityFilterChainExtension extension : extensions) {
                extension.configure(http);
            }
        return http.build();
    }
}