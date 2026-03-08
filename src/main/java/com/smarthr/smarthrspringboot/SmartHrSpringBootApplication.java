package com.smarthr.smarthrspringboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.smarthr.smarthrspringboot.repository")
public class SmartHrSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartHrSpringBootApplication.class, args);
    }

}