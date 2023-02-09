package com.example.jkeduhomepage.module.article.repository;

import com.example.jkeduhomepage.module.article.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {
}
