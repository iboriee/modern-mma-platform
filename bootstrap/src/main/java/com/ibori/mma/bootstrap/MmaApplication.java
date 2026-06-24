package com.ibori.mma.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ibori.mma")
@EntityScan(basePackages = "com.ibori.mma")
@EnableJpaRepositories(basePackages = "com.ibori.mma")
public class MmaApplication {
    public static void main(String[] args) {
        SpringApplication.run(MmaApplication.class, args);
    }
}