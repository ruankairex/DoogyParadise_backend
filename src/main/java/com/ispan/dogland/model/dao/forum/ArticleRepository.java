package com.ispan.dogland.model.dao.forum;

import com.ispan.dogland.model.entity.forum.Articles;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Articles, Integer>{

    @Query("SELECT a FROM Articles a ORDER BY a.articleCreateTime ASC")
    Page<Articles> findAllOrderByTime(Pageable pageable);

    @Query("SELECT a FROM Articles a JOIN a.user u ON u.userId = :userId")
    List<Articles> findAllByUserId(@Param("userId") Integer userId);
}
