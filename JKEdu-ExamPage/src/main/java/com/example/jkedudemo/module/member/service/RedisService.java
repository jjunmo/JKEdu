//package com.example.jkedudemo.module.member.service;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.stereotype.Service;
//
//import java.time.Duration;
//
//@Service
//@RequiredArgsConstructor
//public class RedisService {
//
//    private final RedisTemplate redisTemplate;
//
//    // 데이터 넣기
//    public void setValues(String refreshToken, Long id){
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        values.set(refreshToken, String.valueOf(id), Duration.ofMinutes(1)); // 1분 뒤 메모리에서 삭제된다.
//    }
//    // 데이터 가져오기
//    public String getValues(String refreshToken){
//        ValueOperations<String, String> values = redisTemplate.opsForValue();
//        return values.get(refreshToken);
//    }
//}
