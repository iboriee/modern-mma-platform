package com.ibori.framework.response;

import java.util.List;

/**
 * 표준 페이징 응답 포맷
 */
public record PageResponse<T>(
        List<T> content,
        int pageNumber,
        int pageSize,
        long totalElements,
        int totalPages,
        boolean isLast
) {
    // Spring Data의 Page<T> 객체를 이 레코드로 변환하는 팩토리 메서드는
    // framework-web이나 framework-jpa 모듈에서 확장 메서드로 제공할 예정
}