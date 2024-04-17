package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.ArticleComments;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleCommentRepository extends JpaRepository<ArticleComments,Integer> {

    @Query("SELECT c FROM ArticleComments c JOIN c.article a ON a.articleId = :articleId")
    Page<ArticleComments> findAllByArticleId(@Param("articleId") Integer articleId, Pageable pageable);
}
