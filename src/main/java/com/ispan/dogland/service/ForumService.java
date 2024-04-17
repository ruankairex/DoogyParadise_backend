package com.ispan.dogland.service;

import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.forum.ArticleCategoryRepository;
import com.ispan.dogland.model.dao.forum.ArticleCommentRepository;
import com.ispan.dogland.model.dao.forum.ArticleRepository;
import com.ispan.dogland.model.dto.ForumDto;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.forum.ArticleCategory;
import com.ispan.dogland.model.entity.forum.ArticleComments;
import com.ispan.dogland.model.entity.forum.Articles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private ArticleCommentRepository articleCommentRepository;

    @Autowired
    private ArticleCategoryRepository articleCategoryRepository;

    @Autowired
    private UserRepository userRepository;

    public Page<ForumDto> showArticlesByPages(Integer pageNumber){

        Page<Articles> articles = articleRepository.findAllOrderByTime(PageRequest.of(pageNumber, 3));

        Page<ForumDto> forumDtos = articles.map(a -> {
            ForumDto aDto = new ForumDto();
            BeanUtils.copyProperties(a,aDto);
            return aDto;
        });
        return forumDtos;
    }

    public List<Articles> findArticlesByAuthor(Integer userId){
        return articleRepository.findAllByUserId(userId);
    }

    public boolean addNewArticle(Articles articles,Integer userId){
        Users user = userRepository.findByUserId(userId);
        if(user!=null) {
            articles.setUserName(user.getFirstName() + user.getLastName());
            Articles a = articleRepository.save(articles);
            articles.setUser(user);
            articleRepository.save(articles);
            return true;
        }
        return false;
    }

    public Articles editArticle(Articles a){
        return  articleRepository.saveAndFlush(a);
    }

    public Page<ForumDto> showCommentsOfArticle(Integer articleId,Integer pageNumber){
        Sort sortByTime = Sort.by(Sort.Direction.DESC,"commentCreateTime");

        Page<ArticleComments> comments = articleCommentRepository.findAllByArticleId(articleId,PageRequest.of(pageNumber, 4,sortByTime));

        Page<ForumDto> forumDtos = comments.map(a -> {
            ForumDto aDto = new ForumDto();
            BeanUtils.copyProperties(a,aDto);
            return aDto;
        });
        return forumDtos;
    }

    public List<ArticleCategory> showCategories(){
        return articleCategoryRepository.findAll();
    }

}
