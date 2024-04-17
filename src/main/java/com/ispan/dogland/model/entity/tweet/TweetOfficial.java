package com.ispan.dogland.model.entity.tweet;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tweet_official")
public class TweetOfficial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tweetId;
    private Integer employeeId;
    private Integer userId;
    private String tweetContent;
    private String tweetLink;
    private String imgPathLocal;
    private String imgPathCloud;
    private Integer preNode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date postDate;
    private Integer tweetStatus;
    private Integer numReport;


    public TweetOfficial() {
    }

    public Integer getTweetId() {
        return tweetId;
    }

    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
    }

    public String getTweetLink() {
        return tweetLink;
    }

    public void setTweetLink(String tweetLink) {
        this.tweetLink = tweetLink;
    }

    public Integer getPreNode() {
        return preNode;
    }

    public void setPreNode(Integer preNode) {
        this.preNode = preNode;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Integer getTweetStatus() {
        return tweetStatus;
    }

    public void setTweetStatus(Integer tweetStatus) {
        this.tweetStatus = tweetStatus;
    }

    public Integer getNumReport() {
        return numReport;
    }

    public void setNumReport(Integer numReport) {
        this.numReport = numReport;
    }

    public String getImgPathLocal() {
        return imgPathLocal;
    }

    public void setImgPathLocal(String imgPathLocal) {
        this.imgPathLocal = imgPathLocal;
    }

    public String getImgPathCloud() {
        return imgPathCloud;
    }

    public void setImgPathCloud(String imgPathCloud) {
        this.imgPathCloud = imgPathCloud;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetOfficial{");
        sb.append("tweetId=").append(tweetId);
        sb.append(", employeeId=").append(employeeId);
        sb.append(", userId=").append(userId);
        sb.append(", tweetContent='").append(tweetContent).append('\'');
        sb.append(", tweetLink='").append(tweetLink).append('\'');
        sb.append(", imgPathLocal='").append(imgPathLocal).append('\'');
        sb.append(", imgPathCloud='").append(imgPathCloud).append('\'');
        sb.append(", preNode=").append(preNode);
        sb.append(", postDate=").append(postDate);
        sb.append(", tweetStatus=").append(tweetStatus);
        sb.append(", numReport=").append(numReport);
        sb.append('}');
        return sb.toString();
    }
}

