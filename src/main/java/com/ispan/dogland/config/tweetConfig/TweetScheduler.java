package com.ispan.dogland.config.tweetConfig;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class TweetScheduler {

    @Autowired
    private  TweetService tweetService;

    @Autowired
    private RestTemplate restTemplate;

    @Scheduled(cron = "0 0 2 * * *", zone="Asia/Taipei")
    public void sendTweetToFlask() {
        String result = sendToFlask();
        System.out.println("Tweet sent to Flask at: " + LocalDateTime.now());
    }

    // 此處放置你的方法
    @Async
    public String sendToFlask() {
        // Flask 應用程式的 URL
        String flaskUrl = "http://localhost:5000/api/process_sentence";
        // 設置headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 準備請求內容
        List<Tweet> tweets = tweetService.findAllTweetsOnly();
        List<String> sentences = new ArrayList<>();
        for (Tweet tweet : tweets) {
            sentences.add(tweet.getTweetContent());
        }
        // 創建 ObjectMapper 實例
        ObjectMapper objectMapper = new ObjectMapper();
        // 將 sentences 轉換為 JSON 字符串
        String sentencesJson;
        try {
            sentencesJson = objectMapper.writeValueAsString(sentences);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 處理序列化時的異常
            return "Error occurred during JSON serialization";
        }
        String requestBody = "{\n" +
                "    \"sentences\": " + sentencesJson + "\n" +
                "}";
        // 發送 POST 請求到 Flask 應用程式
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String result = restTemplate.postForObject(flaskUrl, requestEntity, String.class);
        // 回傳 Flask 的回應
        return result;
    }
}
