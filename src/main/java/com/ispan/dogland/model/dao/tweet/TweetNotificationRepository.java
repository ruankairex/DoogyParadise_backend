package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetNotification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TweetNotificationRepository extends JpaRepository<TweetNotification, Integer> {

    List<TweetNotification> findByUserId(Integer userId);

    List<TweetNotification> findByUserIdAndTweetId(Integer userId,Integer tweetId);

    TweetNotification findByTweetNotiId(Integer tweetNotiId);
}
