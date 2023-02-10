package com.example.jkeduhomepage.module.article.dto;


import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {
    private Long id;

    private String title;

    private String content;

    private List<UploadFile> uploadFileList;

    private String category;

    public static ArticleResponseDTO saveArticle(Article article){
        return ArticleResponseDTO.builder()
                .title(article.getTitle())
                .content(article.getContent())
                .build();
    }
}
