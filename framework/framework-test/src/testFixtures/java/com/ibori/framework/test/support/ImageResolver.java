package com.ibori.framework.test.support;

import java.io.InputStream;
import java.util.Properties;

public class ImageResolver {
    public static String get(String key, String defaultValue) {
        try (InputStream is = ImageResolver.class.getResourceAsStream("/testcontainers.properties")) {
            if (is != null) {
                Properties props = new Properties();
                props.load(is);
                return props.getProperty(key, defaultValue);
            }
        } catch (Exception e) {
            // 파일이 없거나 읽기 실패하면 기본값 반환
        }
        return defaultValue;
    }
}