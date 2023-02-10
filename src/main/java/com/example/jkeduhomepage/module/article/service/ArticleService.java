package com.example.jkeduhomepage.module.article.service;

import com.amazonaws.services.kms.model.NotFoundException;
import com.example.jkeduhomepage.module.article.dto.ArticleRequestDTO;
import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.article.repository.ArticleRepository;
import com.example.jkeduhomepage.module.config.SecurityUtil;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.example.jkeduhomepage.module.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final MemberRepository memberRepository;

    public Member isMemberCurrent() {
        return memberRepository.findById(SecurityUtil.getCurrentMemberId())
                .orElseThrow(() -> new NotFoundException("로그인 유저 정보가 없습니다"));
    }

    public void saveArticle(Member member, ArticleRequestDTO articleRequestDTO){
        Article article=new Article();
        article.setTitle(articleRequestDTO.getTitle());
        article.setContent(articleRequestDTO.getContent());
        article.setMember(member);
        articleRepository.save(article);
    }

}
