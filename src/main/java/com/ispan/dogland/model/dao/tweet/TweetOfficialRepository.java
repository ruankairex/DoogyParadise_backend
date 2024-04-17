package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetOfficial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TweetOfficialRepository extends JpaRepository<TweetOfficial, Integer> {
    @Query("select t from TweetOfficial t where t.tweetStatus = 1 order by t.tweetId desc")
    List<TweetOfficial> findAllOfficialTweetWhereStatusIs1();
    TweetOfficial findByTweetId(Integer tweetOfficialId);

    @Query("select t from TweetOfficial t  order by t.tweetId desc limit 3")
    List<TweetOfficial> findLast3();

}
