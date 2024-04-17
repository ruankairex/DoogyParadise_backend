package com.ispan.dogland.model.dto;

public class TweetLikesCheckResponse {
    private Integer tweetLikesNum;
    private Integer isUserLiked;

    public TweetLikesCheckResponse() {
    }

    public TweetLikesCheckResponse(Integer tweetLikesNum, Integer isUserLiked) {
        this.tweetLikesNum = tweetLikesNum;
        this.isUserLiked = isUserLiked;
    }

    public Integer getTweetLikesNum() {
        return tweetLikesNum;
    }

    public void setTweetLikesNum(Integer tweetLikesNum) {
        this.tweetLikesNum = tweetLikesNum;
    }

    public Integer getIsUserLiked() {
        return isUserLiked;
    }

    public void setIsUserLiked(Integer isUserLiked) {
        this.isUserLiked = isUserLiked;
    }
}
