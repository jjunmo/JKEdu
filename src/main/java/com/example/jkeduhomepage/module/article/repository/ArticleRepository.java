package com.example.jkeduhomepage.module.article.repository;

import com.example.jkeduhomepage.module.article.entity.Article;
import com.example.jkeduhomepage.module.common.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article,Long> {

    Slice<Article> findByCategoryOrderByIdAsc(Category category , Pageable pageable);
}
