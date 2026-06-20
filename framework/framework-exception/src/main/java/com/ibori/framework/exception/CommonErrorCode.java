package com.ibori.framework.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * 프레임워크 전역에서 사용하는 공통 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    INVALID_INPUT_VALUE("COMMON-400", "입력값이 유효하지 않습니다.", HttpStatus.BAD_REQUEST),
    MISSING_REQUEST_PARAMETER("COMMON-400-1", "필수 요청 파라미터가 누락되었습니다.", HttpStatus.BAD_REQUEST),
    INVALID_TYPE_VALUE("COMMON-400-2", "요청 파라미터의 타입이 올바르지 않습니다.", HttpStatus.BAD_REQUEST),
    UNREADABLE_REQUEST_BODY("COMMON-400-3", "요청 본문을 읽을 수 없습니다.", HttpStatus.BAD_REQUEST),
    NOT_FOUND("COMMON-404", "요청하신 리소스를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),
    METHOD_NOT_ALLOWED("COMMON-405", "지원하지 않는 HTTP 메서드입니다.", HttpStatus.METHOD_NOT_ALLOWED),
    UNSUPPORTED_MEDIA_TYPE("COMMON-415", "지원하지 않는 미디어 타입입니다.", HttpStatus.UNSUPPORTED_MEDIA_TYPE),
    INTERNAL_SERVER_ERROR("SYS-500", "서버 내부 오류가 발생했습니다. 관리자에게 문의하세요.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;
}