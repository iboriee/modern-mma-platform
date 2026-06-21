package com.ibori.framework.logging.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID_MDC_KEY = "request_id";
    private static final String API_REQUEST_LOG_FORMAT = "[API] [{}] {} {} | Req Body: {} | Status: {} | Res Body: {} | Time: {}ms";
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String requestId = UUID.randomUUID().toString().substring(0, 8);
        MDC.put(REQUEST_ID_MDC_KEY, requestId);

        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper cachingResponse = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(cachingRequest, cachingResponse);
            cachingResponse.copyBodyToResponse();
        } finally {
            long duration = System.currentTimeMillis() - startTime;

            if( cachingResponse.getStatus() >= 400 ) {
                String requestBody = getPayload(cachingRequest.getContentAsByteArray(), cachingRequest.getCharacterEncoding());
                String responseBody = getPayload(cachingResponse.getContentAsByteArray(), cachingResponse.getCharacterEncoding());

                log.info(API_REQUEST_LOG_FORMAT,
                        requestId,
                        request.getMethod(),
                        request.getRequestURI(),
                        requestBody,
                        cachingResponse.getStatus(),
                        responseBody,
                        duration
                );
            }

            MDC.clear();
        }
    }

    private String getPayload(byte[] buf, String encoding) {
        if (buf == null || buf.length == 0) return "";
        try {
            int length = Math.min(buf.length, 1024);
            String payload = new String(buf, 0, length, encoding);

            return payload.trim().replaceAll("\\s+", " ");
        } catch (Exception e) {
            return "[Payload Read Error]";
        }
    }

}


