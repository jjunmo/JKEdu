package com.example.jkeduhomepage.module.article.repository;

import com.example.jkeduhomepage.module.article.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {

    List<UploadFile> findByArticle_Id(Long id);

    List<UploadFile> findByArticle_IdNull();
}
