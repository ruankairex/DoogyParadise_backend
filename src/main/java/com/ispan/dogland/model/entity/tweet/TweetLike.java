package com.ispan.dogland.model.entity.tweet;

import jakarta.persistence.*;

@Entity
@Table(name = "tweet_like")
public class TweetLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tweet_like_id")
    private Integer tweetLikeId;

    @Column(name = "tweet_id")
    private Integer tweetId;

    @Column(name = "user_id")
    private Integer userId;

    public TweetLike() {
    }

    public TweetLike(Integer tweetId, Integer userId) {
        this.tweetId = tweetId;
        this.userId = userId;
    }

    public Integer getTweetLikeId() {
        return tweetLikeId;
    }

    public void setTweetLikeId(Integer tweetLikeId) {
        this.tweetLikeId = tweetLikeId;
    }

    public Integer getTweetId() {
        return tweetId;
    }

    public void setTweetId(Integer tweetId) {
        this.tweetId = tweetId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetLike{");
        sb.append("tweetLikeId=").append(tweetLikeId);
        sb.append(", tweetId=").append(tweetId);
        sb.append(", userId=").append(userId);
        sb.append('}');
        return sb.toString();
    }
}
