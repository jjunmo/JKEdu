package com.example.jkeduhomepage.module.article.dto;


import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.example.jkeduhomepage.module.common.enums.Category;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticleResponseDTO {

    private Category category;
    private Long id;

    private String title;

    private String name;

    private String content;

    private LocalDate createdDate;

    private List<UploadFile> uploadFileList;

    public static ArticleResponseDTO articleList(Article article){
        return ArticleResponseDTO.builder()
                .id(article.getId())
                .title(article.getTitle())
                .content(article.getContent())
                .name(article.getMember().getName())
                .createdDate(article.getCreateDate())
                .build();
    }




}
