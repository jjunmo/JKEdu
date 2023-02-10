package com.example.jkeduhomepage.module.article.controller;

import com.example.jkeduhomepage.module.article.service.AwsS3Service;
import com.example.jkeduhomepage.module.common.utility.S3Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final AwsS3Service awsS3Service;

    @PostMapping("/file")
    public ResponseEntity<Object> uploadFile(
            @RequestParam("category") String category,
            @RequestPart(value = "file") MultipartFile multipartFile) {
        Map<String,String> fileMap=new HashMap<>();
        String URL = awsS3Service.uploadFile(category, multipartFile);
        fileMap.put(multipartFile.getOriginalFilename(), URL);
        return ResponseEntity.ok(fileMap);
    }

    @PostMapping("/files")
    public ResponseEntity<List<String>> uploadFile(@RequestParam("category") String category,@RequestPart(value = "file")  List<MultipartFile> multipartFile) {
        return ResponseEntity.ok(awsS3Service.uploadFile(category,multipartFile));
    }

    @DeleteMapping("/file")
    public ResponseEntity<Object> deleteFile(@RequestParam("category") String category,@RequestParam String fileName) {
        awsS3Service.deleteFile(category,fileName);
        return ResponseEntity.ok("삭제 완료");
    }

//    @GetMapping("/download")
//    public ResponseEntity<ByteArrayResource> downloadFile(
//            @RequestParam("resourcePath") String resourcePath) {
//        byte[] data = awsS3Service.downloadFile(resourcePath);
//        ByteArrayResource resource = new ByteArrayResource(data);
//        HttpHeaders headers = buildHeaders(resourcePath, data);
//
//        return ResponseEntity
//                .ok()
//                .headers(headers)
//                .body(resource);
//    }
//
//    private HttpHeaders buildHeaders(String resourcePath, byte[] data) {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentLength(data.length);
//        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//        headers.setContentDisposition(S3Utils.createContentDisposition(resourcePath));
//        return headers;
//    }
}
