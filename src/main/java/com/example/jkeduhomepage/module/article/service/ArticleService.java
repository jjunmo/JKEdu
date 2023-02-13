package com.example.jkeduhomepage.module.article.service;

import com.amazonaws.services.kms.model.NotFoundException;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

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

        Page<ArticleResponseDTO> articlePage=articleRepository.findByCategoryOrderByIdAsc(category,pageable)
                .map(ArticleResponseDTO::articleList);

        return ArticlePageResponseDTO.getPage(articlePage.hasNext(),articlePage.getContent());
    }
}
