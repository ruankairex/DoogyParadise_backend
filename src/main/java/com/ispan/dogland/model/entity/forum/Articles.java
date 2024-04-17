package com.ispan.dogland.model.entity.forum;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="articles")
public class Articles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "article_id")
    private Integer articleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @JoinColumn(name="user_name")
    private String userName;

    @Column(name="article_title")
    private String articleTitle;

    @Column(name="article_create_time")
    private Date articleCreateTime;

    @OneToMany(fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            mappedBy = "commentId")
    @JsonIgnore
    private List<ArticleComments> comments;

    @ManyToOne(fetch = FetchType.EAGER,
             cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private ArticleCategory articleCategory;

    public Articles(){}

    public Articles(Integer articleId, Users user, String userName, String articleTitle, Date articleCreateTime, List<ArticleComments> comments, ArticleCategory articleCategory) {
        this.articleId = articleId;
        this.user = user;
        this.userName = userName;
        this.articleTitle = articleTitle;
        this.articleCreateTime = articleCreateTime;
        this.comments = comments;
        this.articleCategory = articleCategory;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public Date getArticleCreateTime() {
        return articleCreateTime;
    }

    public void setArticleCreateTime(Date articleCreateTime) {
        this.articleCreateTime = articleCreateTime;
    }

    public List<ArticleComments> getComments() {
        return comments;
    }

    public void setComments(List<ArticleComments> comments) {
        this.comments = comments;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }
}
