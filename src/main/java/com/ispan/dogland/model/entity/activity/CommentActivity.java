package com.ispan.dogland.model.entity.activity;

import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
public class CommentActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private VenueActivity venueActivity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    private String commentText;

    private Integer score;

    private String checkResult;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date commentTime;
    ////////////////////////////
    @PostLoad
    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(commentTime==null) {
            commentTime=new Date();
        }
        if(checkResult==null){
            checkResult="其他";
        }
    }
    @PreUpdate
    public void onUpdate(){
        commentTime=new Date();
        if(checkResult==null){
            checkResult="其他";
        }
    }

    //////////////////////////////////
    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public VenueActivity getVenueActivity() {
        return venueActivity;
    }

    public void setVenueActivity(VenueActivity venueActivity) {
        this.venueActivity = venueActivity;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Date getCommentTime() {
        return commentTime;
    }

    public void setCommentTime(Date commentTime) {
        this.commentTime = commentTime;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }
}
