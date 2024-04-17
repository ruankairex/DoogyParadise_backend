package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.TweetLikesCheckResponse;
import com.ispan.dogland.model.dto.UserDto;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetOfficial;
import com.ispan.dogland.service.interfaceFile.AccountService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tweet")
public class TweetControllerOpen {
    @Autowired
    private TweetService tweetService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/getTweetById/{tweetId}")
    public Tweet getTweetById(@PathVariable Integer tweetId) {
        return tweetService.findTweetByTweetId(tweetId);
    }



    @GetMapping("/getAllTweet")
    public List<Tweet> allTweet(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "6") int limit) {
        return tweetService.getAllTweetForPage(page, limit);
    }

    @GetMapping("/getImage/{fileName}")
    public ResponseEntity<byte[]> getImage(@PathVariable String fileName) {
        String imagePath = "D:/image/" + fileName;

        try {
            Path filePath = Paths.get(imagePath);
            byte[] imageBytes = Files.readAllBytes(filePath);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    //取得回文的數量
    @GetMapping("/getNumOfComment/{tweetId}")
    public Integer getNumOfComment(@PathVariable Integer tweetId) {
        return tweetService.getNumOfComment(tweetId).size(); // Assuming tweetService has a method to get the number of comments for a tweet
    }

    ////取得回文的內容
    @GetMapping("/getComments/{tweetId}")
    public List<Tweet> getComments(@PathVariable Integer tweetId) {
        return tweetService.getNumOfComment(tweetId); // Assuming tweetService has a method to get the number of comments for a tweet
    }

    @GetMapping("/getUserNameByTweetId/{tweetId}")
    public String getUserNameByTweetId(@PathVariable Integer tweetId) {
        return tweetService.findUserByTweetId(tweetId).getLastName();
    }

    @GetMapping("/getUserByTweetId/{tweetId}")
    public UserDto getUserByTweetId(@PathVariable Integer tweetId) {
        Users user = tweetService.findUserByTweetId(tweetId);
        UserDto userDto = new UserDto();
        userDto.setUserId(user.getUserId());
        userDto.setLastName(user.getLastName());
        userDto.setUserImgPath(user.getUserImgPath());
        return userDto;
    }

    @GetMapping("/getUserIdByTweetId/{tweetId}")
    public String getUserIdByTweetId(@PathVariable Integer tweetId) {
        return tweetService.findUserByTweetId(tweetId).getUserId().toString();
    }

    @GetMapping("/getTweetDogTags/{tweetId}")
    public List<Dog> getTweetDogTags(@PathVariable Integer tweetId) {
        return tweetService.findTweetDogsByTweetId(tweetId);

    }


    @GetMapping("/getTweetLikesNumForVisitor")
    public ResponseEntity<TweetLikesCheckResponse> getTweetLikesNum(@RequestParam Integer tweetId) {
        List<Users> users = tweetService.findUserLikesByTweetId(tweetId);

        TweetLikesCheckResponse tweetLikesCheckResponse = new TweetLikesCheckResponse();

        tweetLikesCheckResponse.setTweetLikesNum(tweetService.findUserLikesByTweetId(tweetId).size());

        return ResponseEntity.ok(tweetLikesCheckResponse);
    }

    //取得該貼文所有按讚的用戶
    @GetMapping("/getTweetLikesUser")
    public ResponseEntity<List<UserDto>> getTweetLikesUser(@RequestParam Integer tweetId) {
        List<Users> users = tweetService.findUserLikesByTweetId(tweetId);
        List<UserDto> userDtos= new ArrayList<>();

        UserDto userDto = null;
        for (Users user : users) {
            userDto = new UserDto();
            userDto.setUserWithOutPassword(user);
            userDtos.add(userDto);
        }
        return ResponseEntity.ok(userDtos);
    }

    @GetMapping("/getTweetOfficialTop3")
    public ResponseEntity<List<TweetOfficial>> getTweetOfficialTop3() {
        return ResponseEntity.ok(tweetService.findLast3TweetOfficial());
    }
}
