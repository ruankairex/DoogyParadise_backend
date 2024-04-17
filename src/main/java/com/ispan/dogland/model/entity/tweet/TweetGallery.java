package com.ispan.dogland.model.entity.tweet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "tweet_gallery")
public class TweetGallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer imgId;


    private String imgPath;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "tweet_id")
    @JsonIgnore
    private Tweet tweet;


    public TweetGallery() {
    }

    public Integer getImgId() {
        return imgId;
    }

    public void setImgId(Integer imgId) {
        this.imgId = imgId;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetGallery{");
        sb.append("imgId=").append(imgId);
        sb.append(", imgPath='").append(imgPath).append('\'');
        sb.append(", tweet=").append(tweet);
        sb.append('}');
        return sb.toString();
    }
}
