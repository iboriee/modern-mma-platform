package com.ibori.framework.web.config;

import com.ibori.framework.web.client.HttpInterfaceFactory;
import com.ibori.framework.web.client.RestClientConfig;
import com.ibori.framework.web.error.CustomErrorController;
import com.ibori.framework.web.exception.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Import;

/**
 * 프레임워크 WEB 모듈 자동 설정 클래스
 */
@AutoConfiguration
@Import({GlobalExceptionHandler.class,
        RestClientConfig.class,
        HttpInterfaceFactory.class,
        CustomErrorController.class
})
public class WebFrameworkAutoConfiguration {

    // todo: 공통 인터셉터, CORS 설정, ObjectMapper 커스텀 설정... 추가
}
