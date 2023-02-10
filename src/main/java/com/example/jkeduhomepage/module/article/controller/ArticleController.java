package com.example.jkeduhomepage.module.article.controller;

import com.example.jkeduhomepage.module.article.dto.ArticleRequestDTO;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.example.jkeduhomepage.module.article.service.ArticleService;
import com.example.jkeduhomepage.module.article.service.AwsS3Service;
import com.example.jkeduhomepage.module.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final AwsS3Service awsS3Service;
    private final ArticleService articleService;


    @PostMapping
    public ResponseEntity<Object> save(@RequestBody ArticleRequestDTO articleRequestDTO){
        Member member=articleService.isMemberCurrent();

        if(articleRequestDTO.getTitle().equals(""))
            return ResponseEntity.badRequest().body("제목을 작성하세요");

        articleService.saveArticle(member,articleRequestDTO);

        return ResponseEntity.ok().body("저장 완료");
    }



    @PostMapping("/file")
    public ResponseEntity<List<UploadFile>> uploadFile(@RequestParam("category") String category,@RequestPart(value = "file")  List<MultipartFile> multipartFile) {
//        Member member=articleService.isMemberCurrent();
        if(category.equalsIgnoreCase("GALLERY")){
            throw new RuntimeException("썸넬 이미지 만들어야함");
        }

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
