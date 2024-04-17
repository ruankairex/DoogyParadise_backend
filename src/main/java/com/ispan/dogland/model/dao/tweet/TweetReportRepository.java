package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TweetReportRepository extends JpaRepository<TweetReport, Integer> {

    @Query("SELECT t FROM TweetReport t WHERE t.reportsId = ?1")
    TweetReport findByTweetReportId(Integer id);

    @Query("SELECT r FROM TweetReport r JOIN FETCH r.tweet t JOIN FETCH r.reporter p WHERE t.tweetId = ?1 AND p.userId = ?2")
    TweetReport findByTweetIdAndUserId(Integer tweetId, Integer userId);
}
