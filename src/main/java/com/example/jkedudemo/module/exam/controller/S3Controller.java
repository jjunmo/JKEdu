package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.service.S3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Controller
@RequestMapping("/csv_read")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping
    public HttpEntity<String> read() throws IOException {
        s3Service.readObject("jkeduexam/examCategory.csv");
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
