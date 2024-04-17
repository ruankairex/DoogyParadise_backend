package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TweetLikeRepository extends JpaRepository<TweetLike, Integer> {

    @Query("SELECT t FROM TweetLike t WHERE t.tweetId = ?1 AND t.userId = ?2")
    TweetLike findByTweetIdAndUserId(Integer tweetId, Integer userId);

}
