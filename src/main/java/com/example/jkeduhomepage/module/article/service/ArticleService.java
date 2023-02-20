package com.example.jkeduhomepage.module.article.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.example.jkeduhomepage.module.article.dto.ArticlePageResponseDTO;
import com.example.jkeduhomepage.module.article.dto.ArticleRequestDTO;
import com.example.jkeduhomepage.module.article.dto.ArticleResponseDTO;
import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.example.jkeduhomepage.module.article.repository.ArticleRepository;
import com.example.jkeduhomepage.module.article.repository.UploadFileRepository;
import com.example.jkeduhomepage.module.common.enums.Category;
import com.example.jkeduhomepage.module.config.SecurityUtil;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final UploadFileRepository uploadFileRepository;

    private final AmazonS3Client amazonS3Client;
    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    private static final String CATEGORY_PREFIX = "/";

    private final MemberRepository memberRepository;

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NotFoundException("로그인 유저 정보가 없습니다"));
    }

    @Transactional
    public ArticleResponseDTO saveArticle(Category category, Member member, ArticleRequestDTO articleRequestDTO){
        List<UploadFile> paramUploadFileList = articleRequestDTO.getUploadFileList();

        Article articleParam = articleRequestDTO.doToEntity(category,member,paramUploadFileList);

        Article article=articleRepository.save(articleParam);

        return article.entityToDto(article);

    }

    public ArticlePageResponseDTO categoryList(Category category, Pageable pageable){

        Page<ArticleResponseDTO> articlePage=articleRepository.findByCategoryOrderByIdDesc(category,pageable)
                .map(ArticleResponseDTO::paramArticle);

        return ArticlePageResponseDTO.getPage(articlePage);
    }

    public Optional<Article> getArticle(Long id){
        return articleRepository.findById(id);
    }

    @Transactional
    public void delete_Article(Category category,Long id) {
        List<UploadFile> uploadFileList=uploadFileRepository.findByArticle_Id(id);

        uploadFileList.forEach(uploadFile -> deleteFile(category, uploadFile.getFileName()));

        articleRepository.deleteById(id);
    }


    public void deleteFile(Category category,String fileName) {
        String deleteFileName = category + CATEGORY_PREFIX + fileName;
        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, deleteFileName));
    }


}
