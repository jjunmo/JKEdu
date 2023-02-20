package com.example.jkeduhomepage.module.article.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ArticlePageResponseDTO {

    public boolean next;

    public int nowPage;

    List<ArticleResponseDTO> articleResponseDTOList;

    public static ArticlePageResponseDTO getPage(Page<ArticleResponseDTO> page){
        return ArticlePageResponseDTO.builder()
                .next(page.hasNext())
                .nowPage(page.getNumber()+1)
                .articleResponseDTOList(page.getContent())
                .build();
    }
}
