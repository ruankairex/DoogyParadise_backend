package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetFollowList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TweetFollowListRepository extends JpaRepository<TweetFollowList, Integer> {

    @Query("SELECT t FROM TweetFollowList t WHERE t.userId = ?1 AND t.follwerId = ?2")
    TweetFollowList findByMyUserIdAndOtherUserId(Integer userId,Integer followerId);

    @Transactional
    void deleteByUserIdAndFollwerId(Integer userId,Integer follwerId);

    List<TweetFollowList> findByUserId(Integer userId);
}
