package com.ibori.framework.web.client;

import com.ibori.framework.exception.BusinessException;
import com.ibori.framework.exception.CommonErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import java.time.Duration;

/**
 * 공통 HTTP 클라이언트 (RestClient) 설정
 */
@Slf4j
@AutoConfiguration
public class RestClientConfig {

    private static final String EXTERNAL_API_LOG_FORMAT = "[External API Request] {} {}";

    @Bean
    public RestClientCustomizer defaultRestClientCustomizer() {
        return builder -> {
            JdkClientHttpRequestFactory requestFactory = new JdkClientHttpRequestFactory();
            requestFactory.setReadTimeout(Duration.ofSeconds(5));   //todo: 타임아웃 조절
            builder.requestFactory(requestFactory)
                    .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                    .defaultStatusHandler(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            (request, response) -> {
                                log.error("[External API Error] Status: {}, URI: {}", response.getStatusCode(), request.getURI());
                                throw new BusinessException(CommonErrorCode.INTERNAL_SERVER_ERROR, "외부 연동 API 호출 중 오류가 발생했습니다.");
                            }
                    )
                    .requestInterceptor((request, body, execution) -> {
                        if (log.isDebugEnabled()) {
                            log.debug(EXTERNAL_API_LOG_FORMAT, request.getMethod(), request.getURI());
                        }
                        return execution.execute(request, body);
                    });
        };
    }
}
