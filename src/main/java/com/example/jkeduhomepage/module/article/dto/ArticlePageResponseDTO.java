package com.example.jkeduhomepage.module.article.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticlePageResponseDTO {

    boolean next;

    List<ArticleResponseDTO> articleResponseDTOList;

    public static ArticlePageResponseDTO getPage(boolean next , List<ArticleResponseDTO> articleResponseDTOList ){
        return ArticlePageResponseDTO.builder()
                .next(next)
                .articleResponseDTOList(articleResponseDTOList)
                .build();
    }
}
