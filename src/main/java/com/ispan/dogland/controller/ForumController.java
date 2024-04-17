package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.ForumDto;
import com.ispan.dogland.model.entity.forum.ArticleCategory;
import com.ispan.dogland.service.ForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/forum")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class ForumController {

    @Autowired
    private ForumService fs;

    @GetMapping("/{pageNumber}")
    Page<ForumDto> forumLobby(@PathVariable Integer pageNumber){
        return fs.showArticlesByPages(pageNumber);
    }

    @GetMapping("/{articleId}/{pageNumber}")
    Page<ForumDto> commentsOfArticle(@PathVariable Integer articleId,@PathVariable Integer pageNumber){
        return fs.showCommentsOfArticle(articleId,pageNumber);
    }

    @GetMapping("/category")
    List<ArticleCategory> getArticleCategory(){
        return fs.showCategories();
    }
}
