package com.example.jkeduhomepage.module.article.controller;

import com.example.jkeduhomepage.module.article.dto.ArticleRequestDTO;
import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.example.jkeduhomepage.module.article.service.ArticleService;
import com.example.jkeduhomepage.module.article.service.AwsS3Service;
import com.example.jkeduhomepage.module.common.enums.Category;
import com.example.jkeduhomepage.module.common.enums.Role;
import com.example.jkeduhomepage.module.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import static com.example.jkeduhomepage.module.article.dto.ArticleResponseDTO.oneArticle;

@RequiredArgsConstructor
@Controller
@RequestMapping("/article")
public class ArticleController {

    private final AwsS3Service awsS3Service;
    private final ArticleService articleService;

    /**
     * 게시글 쓰기
     * @param category 해당 카테고리
     * @param articleRequestDTO 제목 , 내용 , 파일
     * @return 게시글 작성
     */
    @PostMapping("/{category}")
    public ResponseEntity<Object> save(@PathVariable Category category, @RequestBody ArticleRequestDTO articleRequestDTO){
        Member member=articleService.isMemberCurrent();

        if(articleRequestDTO.getTitle().equals(""))
            return ResponseEntity.badRequest().body("제목을 작성하세요");
        if(articleRequestDTO.getContent().equals(""))
            return ResponseEntity.badRequest().body("내용을 입력하세요");

        if(member.getRole().equals(Role.ROLE_USER)){
            return new ResponseEntity<>("권한없음 :)",HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok().body(articleService.saveArticle(category,member,articleRequestDTO));
    }

    /**
     * 게시글 리스트
     * @param category 게시글 카테고리
     * @param pageable 페이지
     * @return 게시글 리스트
     */
    @GetMapping("/{category}")
    public ResponseEntity<Object> categoryList(@PathVariable Category category, @PageableDefault(size = 5) Pageable pageable){
        if(!category.equals(Category.NOTICE)){
            articleService.isMemberCurrent();
        }
        return ResponseEntity.ok().body(articleService.categoryList(category,pageable));
    }


    /**
     * 게시글 보기
     * @param category 게시글 카테고리
     * @param id 게시글 번호
     * @return 해당 게시글
     */
    @GetMapping("/{category}/{id}")
    public ResponseEntity<Object> getArticle(@PathVariable Category category,@PathVariable Long id){

        if(!category.equals(Category.NOTICE)){
            articleService.isMemberCurrent();
        }

        Optional<Article> articleOptional=articleService.getArticle(id);

        return articleOptional
                .<ResponseEntity<Object>>map(article -> ResponseEntity.ok(oneArticle(article)))
                .orElseGet(() -> ResponseEntity.badRequest().body("해당 게시글은 존재하지 않습니다."));
    }


    /**
     * 게시글 삭제
     * @param category 게시글 카테고리
     * @param id 게시글 번호
     * @return 삭제
     */
    @DeleteMapping("/{category}/{id}")
    public ResponseEntity<Object> deleteArticle(@PathVariable Category category,@PathVariable Long id) {
        Member member=articleService.isMemberCurrent();

        if(!member.getRole().equals(Role.ROLE_ADMIN)){
            return new ResponseEntity<>("권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        articleService.delete_Article(category,id);
        return ResponseEntity.ok("글 삭제 완료");
    }


    /**
     * S3 파일 업로드
     * @param filePath 파일 저장 경로
     * @param multipartFile 파일
     * @return 업로드 완료
     */
    @PostMapping("/file")
    public ResponseEntity<List<UploadFile>> uploadFile(@RequestParam("category") String filePath, @RequestPart(value = "file")  List<MultipartFile> multipartFile) {

        Member member=articleService.isMemberCurrent();
        if(member.getRole().equals(Role.ROLE_USER)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        return ResponseEntity.ok(awsS3Service.uploadFile(filePath,multipartFile));
    }

//    @DeleteMapping("/file")
//    public ResponseEntity<Object> deleteFile(@RequestParam("category") String filePath, @RequestParam String fileName) {
//        awsS3Service.deleteFile(filePath,fileName);
//        return ResponseEntity.ok("삭제 완료");
//    }




}
