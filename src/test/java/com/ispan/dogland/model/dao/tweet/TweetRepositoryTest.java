package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.Tweet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TweetRepositoryTest {

    @Autowired
    private TweetRepository tweetRepository;

    @Test
    public void findAllTweetsWithGallery() {
        List<Tweet> tweets = tweetRepository.findAllTweetsWithGallery();
        if (!tweets.isEmpty()) {
            Tweet lastTweet = tweets.get(tweets.size() - 1);
            System.out.println(lastTweet.getTweetContent());
            // 使用最后一条推文进行接下来的操作
        } else {
            // 处理列表为空的情况
        }
        assertNotNull(tweets);
    }

}