package com.ibori.framework.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;
import java.util.List;

/**
 * API 공통 응답 포맷
 * @param <T> 응답 데이터 타입 todo: timezone 고려(클라우드 환경)
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(
        boolean success,
        String code,
        String message,
        T data,
        List<ValidationError> errors,
        LocalDateTime timestamp
) {
    // 성공 응답 (데이터가 있는 경우)
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(
                true,
                "20000", // 표준 성공 코드 (예시)
                "요청이 성공적으로 처리되었습니다.",
                data,
                null,
                LocalDateTime.now()
        );
    }

    // 성공 응답 (데이터가 있는 경우 &  코드 커스텀)
    public static <T> ApiResponse<T> success(String code, String message, T data) {
        return new ApiResponse<>(
                true,
                code,
                message,
                data,
                null,
                LocalDateTime.now()
        );
    }

    // 성공 응답 timestamp 직접 주입
    public static <T> ApiResponse<T> success(T data, LocalDateTime timestamp) {
        return new ApiResponse<>(
                true,
                "20000",
                "요청이 성공적으로 처리되었습니다.",
                data,
                null,
                timestamp);
    }

    // 성공 응답 (데이터가 없는 경우 - Delete, Update 등)
    public static ApiResponse<Void> successNoData() {
        return ApiResponse.success(null);
    }

    // 에러 응답 timestamp 직접 주입
    public static <T> ApiResponse<T> error(String code, String message, LocalDateTime timestamp) {
        return new ApiResponse<>(
                false,
                code,
                message,
                null,
                null,
                timestamp
        );
    }

    // 에러 응답
    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(
                false,
                code,
                message,
                null,
                null,
                LocalDateTime.now()
        );
    }

    // vaildation 실패 응답
    public static ApiResponse<Void> error(String code, String message, List<ValidationError> errors) {
        return new ApiResponse<>(
                false,
                code, message,
                null,
                errors,
                LocalDateTime.now()
        );
    }
}