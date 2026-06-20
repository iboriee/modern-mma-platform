package com.ibori.mma.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.ibori.mma")
public class MmaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MmaApplication.class, args);
    }
}