package com.ispan.dogland.model.dto;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetGallery;
import com.ispan.dogland.model.entity.tweet.TweetReport;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

public class TweetDto {
    private Integer tweetId;
    private Users user;
    private String userName;
    private String tweetContent;
    private Integer preNode;
    private Date postDate;
    private Integer tweetStatus;
    private Integer numReport;
    private List<TweetGallery> tweetGalleries;
    private List<Users> userLikes;
    private List<Dog> dogs;
    private List<TweetReport> tweetReports;

    public TweetDto() {
    }

    public Integer getTweetId() {
        return tweetId;
    }

    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
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

    public String getTweetContent() {
        return tweetContent;
    }

    public void setTweetContent(String tweetContent) {
        this.tweetContent = tweetContent;
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

    public List<TweetGallery> getTweetGalleries() {
        return tweetGalleries;
    }

    public void setTweetGalleries(List<TweetGallery> tweetGalleries) {
        this.tweetGalleries = tweetGalleries;
    }

    public List<Users> getUserLikes() {
        return userLikes;
    }

    public void setUserLikes(List<Users> userLikes) {
        this.userLikes = userLikes;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }

    public List<TweetReport> getTweetReports() {
        return tweetReports;
    }

    public void setTweetReports(List<TweetReport> tweetReports) {
        this.tweetReports = tweetReports;
    }

    public void setTweet(Tweet tweet) {
        this.tweetId = tweet.getTweetId();
        this.user = tweet.getUser();
        this.userName = tweet.getUserName();
        this.tweetContent = tweet.getTweetContent();
        this.preNode = tweet.getPreNode();
        this.postDate = tweet.getPostDate();
        this.tweetStatus = tweet.getTweetStatus();
        this.numReport = tweet.getNumReport();
        this.tweetGalleries = tweet.getTweetGalleries();
        this.userLikes = tweet.getUserLikes();
        this.dogs = tweet.getDogs();
        this.tweetReports = tweet.getTweetReports();
    }
}
