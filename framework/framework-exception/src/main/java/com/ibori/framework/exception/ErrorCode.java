package com.ibori.framework.exception;

import org.springframework.http.HttpStatus;

/**
 * 표준 에러 코드 인터페이스
 * 각 마이크로서비스의 ErrorCode Enum 은 이 인터페이스를 구현.
 */
public interface ErrorCode {
    String getCode();
    String getMessage();
    HttpStatus getStatus();
}
