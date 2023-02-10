package com.example.jkeduhomepage.module.article.dto;


import com.example.jkeduhomepage.module.article.entity.UploadFile;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ArticleRequestDTO {

    private String title="";

    private String content="";

    private List<UploadFile> uploadFileList=new ArrayList<>();

}
