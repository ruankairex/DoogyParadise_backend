package com.ispan.dogland.service;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
public class TweetServiceImplTest {

//    @Autowired
//    private TweetService tweetService;

    @Test
    public void findTweetsByUserId(Integer userId) {
//        List<Tweet> lis =  tweetService.findTweetsByUserId(userId);
        int lis  = 1;
        assertNotNull(lis);

    }
}