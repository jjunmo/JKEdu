package com.example.jkeduhomepage.module.article.dto;


import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.example.jkeduhomepage.module.common.enums.Category;
import com.example.jkeduhomepage.module.member.entity.Member;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleRequestDTO {

    private String title="";

    private String content="";

    private List<UploadFile> uploadFileList=new ArrayList<>();

    public Article doToEntity(Category category,Member member,List<UploadFile> uploadFileList){
        Article article=new Article();
        article.setCategory(category);
        article.setTitle(title);
        article.setContent(content);
        article.setMember(member);
        article.setUploadFileList(uploadFileList);
        return article;
    }


}
