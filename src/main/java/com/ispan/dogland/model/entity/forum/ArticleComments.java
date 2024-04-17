package com.ispan.dogland.model.entity.forum;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="article_comment")
public class ArticleComments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Integer commentId;

    @Column(name="comment_content")
    private String commentContent;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id", referencedColumnName = "article_id")
    private Articles article;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @Column(name="comment_create_time")
    private Date commentCreateTime;

    public ArticleComments(){}

    public ArticleComments(Integer commentId, String commentContent, Articles article, Users user, Date commentCreateTime) {
        this.commentId = commentId;
        this.commentContent = commentContent;
        this.article = article;
        this.user = user;
        this.commentCreateTime = commentCreateTime;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Articles getArticle() {
        return article;
    }

    public void setArticle(Articles article) {
        this.article = article;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getCommentCreateTime() {
        return commentCreateTime;
    }

    public void setCommentCreateTime(Date commentCreateTime) {
        this.commentCreateTime = commentCreateTime;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }
}
