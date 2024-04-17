package com.ispan.dogland.model.entity.tweet;

import jakarta.persistence.*;

@Entity
@Table(name = "tweet_follow_list")
public class TweetFollowList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer followListId;

    private Integer userId;

    private Integer follwerId;

    public TweetFollowList() {
    }

    public TweetFollowList(Integer userId, Integer follwerId) {
        this.userId = userId;
        this.follwerId = follwerId;
    }

    public Integer getFollowListId() {
        return followListId;
    }

    public void setFollowListId(Integer followListId) {
        this.followListId = followListId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getFollwerId() {
        return follwerId;
    }

    public void setFollwerId(Integer followerId) {
        this.follwerId = followerId;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetFollowList{");
        sb.append("followListId=").append(followListId);
        sb.append(", userId=").append(userId);
        sb.append(", follwerId=").append(follwerId);
        sb.append('}');
        return sb.toString();
    }
}