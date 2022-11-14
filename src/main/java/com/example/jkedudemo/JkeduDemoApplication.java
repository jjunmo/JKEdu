package com.example.jkedudemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Spring S3 업로드

// 생활코딩 docker
// Spring Boot Dockerfile 예제
// Spring Boot + Mysql Docker-compose 예제

@SpringBootApplication
@EnableJpaAuditing
public class JkeduDemoApplication {

    //TODO: JWT 토큰에 name 받기
    public static void main(String[] args) {
        SpringApplication.run(JkeduDemoApplication.class, args);
    }
}
