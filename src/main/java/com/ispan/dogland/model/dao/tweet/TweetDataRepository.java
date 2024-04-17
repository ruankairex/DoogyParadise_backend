package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.mongodb.TweetData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TweetDataRepository extends MongoRepository<TweetData, String> {

    List<TweetData> findAllByOrderByTimestampAsc();

    List<TweetData> findAllByOrderByTimestampDesc();
}
