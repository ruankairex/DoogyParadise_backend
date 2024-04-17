package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.ArticleCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleCategoryRepository extends JpaRepository<ArticleCategory,Integer> {
}
