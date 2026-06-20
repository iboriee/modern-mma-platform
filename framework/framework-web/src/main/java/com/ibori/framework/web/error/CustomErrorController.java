package com.ibori.framework.web.error;

import com.ibori.framework.exception.CommonErrorCode;
import com.ibori.framework.exception.ErrorCode;
import com.ibori.framework.response.ApiResponse;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public ResponseEntity<ApiResponse<Void>> handleError(HttpServletRequest request) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        HttpStatus status = statusObj != null
                ? HttpStatus.valueOf(Integer.parseInt(statusObj.toString()))
                : HttpStatus.INTERNAL_SERVER_ERROR;

        log.warn("Unhandled error routed to /error: status={}, uri={}",
                status, request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI));

        ErrorCode errorCode = (status == HttpStatus.NOT_FOUND)
                ? CommonErrorCode.NOT_FOUND
                : CommonErrorCode.INTERNAL_SERVER_ERROR;

        return ResponseEntity
                .status(status)
                .body(ApiResponse.error(errorCode.getCode(), errorCode.getMessage()));
    }
}