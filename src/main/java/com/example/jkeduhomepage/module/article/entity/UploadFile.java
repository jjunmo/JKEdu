package com.example.jkeduhomepage.module.article.entity;


import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UploadFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;

    private String customFileName;

    private String url;

    private String thumbnailImageUrl;

    @ManyToOne
    @JoinColumn(name="article_id",referencedColumnName = "id")
    private Article article;

}
