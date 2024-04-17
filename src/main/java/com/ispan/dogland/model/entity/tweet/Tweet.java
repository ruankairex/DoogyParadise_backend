package com.ispan.dogland.model.entity.tweet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tweet")
public class Tweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tweetId;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;
    private String userName;
    @Column(length = 281)
    private String tweetContent;
    private Integer preNode;
    private Date postDate;
    private Integer tweetStatus;
    private Integer numReport;
    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "tweet",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<TweetGallery> tweetGalleries;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="tweet_like", joinColumns = @JoinColumn(name="tweet_id"),
                                  inverseJoinColumns = @JoinColumn(name="user_id"))
    @JsonIgnore
    private List<Users> userLikes;

    @ManyToMany(fetch = FetchType.LAZY,
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="tweet_tag_dog", joinColumns = @JoinColumn(name="tweet_id"),
                                     inverseJoinColumns = @JoinColumn(name="dog_id"))
    private List<Dog> dogs;

    @OneToMany(fetch = FetchType.LAZY,
               mappedBy = "tweet",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                          CascadeType.DETACH, CascadeType.REFRESH})
    private List<TweetReport> tweetReports;

    public Tweet() {
    }
    public Tweet(Users user, String userName, String tweetContent, Integer preNode, Date postDate, Integer tweetStatus, Integer numReport) {
        this.user = user;
        this.userName = userName;
        this.tweetContent = tweetContent;
        this.preNode = preNode;
        this.postDate = postDate;
        this.tweetStatus = tweetStatus;
        this.numReport = numReport;
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

    public void addTweetGallery(TweetGallery tweetGallery) {
        if(this.tweetGalleries == null) {
            this.tweetGalleries = new ArrayList<>();
        }
        this.tweetGalleries.add(tweetGallery);
        tweetGallery.setTweet(this);
    }

    public void addUserLike(Users user) {
        if(this.userLikes == null) {
            this.userLikes = new ArrayList<>();
        }
        this.userLikes.add(user);
    }

    public void addDog(Dog dog) {
        if(this.dogs == null) {
            this.dogs = new ArrayList<>();
        }
        this.dogs.add(dog);
    }

    public void addTweetReport(TweetReport tweetReport) {
        if(this.tweetReports == null) {
            this.tweetReports = new ArrayList<>();
        }
        this.tweetReports.add(tweetReport);
        tweetReport.setTweet(this);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Tweet{");
        sb.append("tweetId=").append(tweetId);
        sb.append(", user=").append(user);
        sb.append(", userName='").append(userName).append('\'');
        sb.append(", tweetContent='").append(tweetContent).append('\'');
        sb.append(", preNode=").append(preNode);
        sb.append(", postDate=").append(postDate);
        sb.append(", tweetStatus=").append(tweetStatus);
        sb.append(", numReport=").append(numReport);
        sb.append('}');
        return sb.toString();
    }
}