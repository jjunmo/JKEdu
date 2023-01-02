package com.example.jkedudemo.module.exam.controller;

import com.example.jkedudemo.module.exam.service.S3Service;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/csv_read")
@RequiredArgsConstructor
public class S3Controller {
    private final S3Service s3Service;

    @PostMapping
    @Operation(summary = "문제 저장", description = "CSV Reader")
    public HttpEntity<String> read() throws IOException {
        String folder = "jkeduexam/";
        String csv = ".csv";
        s3Service.readObject(folder+"examCategory"+csv);
        s3Service.readObject(folder+"examQuest"+csv);
        s3Service.readObject(folder+"examMultiple"+csv);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }
}
