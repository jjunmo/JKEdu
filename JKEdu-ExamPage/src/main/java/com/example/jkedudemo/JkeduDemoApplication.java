package com.example.jkedudemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Spring Boot + Mysql Docker-compose 예제

@SpringBootApplication
@EnableJpaAuditing
public class JkeduDemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(JkeduDemoApplication.class, args);
    }
}
