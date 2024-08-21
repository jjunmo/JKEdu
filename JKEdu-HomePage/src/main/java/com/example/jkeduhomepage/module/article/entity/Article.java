package com.example.jkeduhomepage.module.article.entity;

import com.example.jkeduhomepage.module.article.dto.ArticleResponseDTO;
import com.example.jkeduhomepage.module.common.enums.Category;
import com.example.jkeduhomepage.module.common.utility.Basetime;
import com.example.jkeduhomepage.module.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Comment;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//무한 재귀 방지
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@JsonIdentityReference(alwaysAsId = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Article extends Basetime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    @Comment("게시글 번호")
    private Long id;

    @Column(name = "title")
    @Comment("게시글 제목")
    private String title;
    @Lob
    @Column(name = "content")
    @Comment("게시글 내용")
    private String content;

    @Enumerated(EnumType.STRING)
    @Nonnull
    @Comment("게시글 카테고리")
    private Category category;

    @Comment("글쓴이")
    @ManyToOne
    @JoinColumn(name="member_id", referencedColumnName = "id")
    private Member member;

    @Comment("업로드 파일")
    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private List<UploadFile> uploadFileList = new ArrayList<>();


    public ArticleResponseDTO entityToDto (Article article) {
        ArticleResponseDTO articleResponseDTO = new ArticleResponseDTO();
        articleResponseDTO.setCategory(article.category);
        articleResponseDTO.setUploadFileList(article.uploadFileList);
        articleResponseDTO.setId(article.id);
        articleResponseDTO.setTitle(article.title);
        articleResponseDTO.setContent(article.content);
        return articleResponseDTO;
    }

}
