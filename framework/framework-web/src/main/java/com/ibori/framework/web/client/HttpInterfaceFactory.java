package com.ibori.framework.web.client;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * 실제 HTTP 통신을 수행하는 구현체 생성 팩토리
 */
@Component
public class HttpInterfaceFactory {

    private final RestClient.Builder restClientBuilder;

    public HttpInterfaceFactory(RestClient.Builder restClientBuilder) {
        this.restClientBuilder = restClientBuilder;
    }

    /**
     * @param clientClass 통신 인터페이스 클래스 (예: MemberClient.class)
     * @param baseUrl 호출할 타겟 서비스의 Base URL (예: "http://member-service")
     * @return Http Interface 구현체 프록시
     */
    public <T> T createClient(Class<T> clientClass, String baseUrl) {

        RestClient restClient = restClientBuilder.baseUrl(baseUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(clientClass);
    }
}