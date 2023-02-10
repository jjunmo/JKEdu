package com.example.jkeduhomepage.module.article.repository;

import com.example.jkeduhomepage.module.article.entity.UploadFile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UploadFileRepository extends JpaRepository<UploadFile,Long> {
}
