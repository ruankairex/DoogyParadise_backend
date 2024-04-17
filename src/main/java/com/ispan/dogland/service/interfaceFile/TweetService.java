package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.mongodb.TweetData;
import com.ispan.dogland.model.entity.tweet.*;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TweetService {

    //只找該user的主推文
    public List<Tweet> findTweetsByUserId(Integer userId);
    public Users findUserByTweetId(Integer tweetId);

    public List<Tweet> findTweetsByUserName(String userName);

    public List<Tweet> getAllTweet();

    public Tweet postNewTweet(Tweet tweet, Integer userId);

    public String saveTweetImgToLocal(MultipartFile file);


    public List<Tweet> getNumOfComment(Integer tweetId);

    public List<Tweet> getAllTweetForPage(int page, int limit);

    public List<Users> findUserLikesByTweetId(Integer tweetId);


    //按讚，建立tweet與user的關聯
    public void createLinkWithTweetAndLike(Integer tweetId, Integer userId);


    public void removeLinkWithTweetAndLike(Integer tweetId, Integer userId);

    public Tweet findTweetByTweetId(Integer tweetId);

    //確認otherUserId是不是已經被myUserId追蹤
    public boolean checkIsFollow(Integer myUserId, Integer otherUserId);

    public boolean followTweetUser(Integer myUserId, Integer otherUserId);

    public void deleteFollowTweetUser(Integer myUserId, Integer otherUserId);

    public List<Tweet> findAllFollowTweetsByUserId(Integer userId);

    public List<Users> findAllFollowUsersByUserId(Integer userId);

    public List<Dog>  findTweetDogsByTweetId(Integer tweetId);

    public Tweet addDogToTweet(Integer dogId, Integer tweetId);

    public Tweet removeDogFromTweet(Integer dogId, Integer tweetId);

    public void sendPostTweetNotificationToFollower(Integer userId,Integer tweetId);

    public void sendReplyNotificationToTweetOwner(Integer hisUserId,Integer hisTweetId,String myName);

    public void sendLikeNotificationToTweetOwner(Integer hisUserId,Integer hisTweetId,String myName);

    public void sendBanTweetNotificationToUser(Integer userId,Tweet tweet);

    public List<TweetNotification> findMyTweetNotifications(Integer userId);

    //把該user的主推文與回文都找出來
    public List<Tweet> findTweetsAndCommentsByUserId(Integer userId);

    public List<Tweet> findAllTweetsOnly();
    
    public TweetNotification findTweetNotificationByNotifiId(Integer id);

    void saveTweetNotification(TweetNotification t1);

    public Tweet updateTweetContent(Integer tweetId, String newContent);

    public boolean checkUserAndReportRelation(Integer tweetId, Integer userId);

    public TweetReport addReportToTweet(Integer tweetId, Integer userId,String reportText, String reportCheckBox);

    public TweetReport addAiReportToTweet(TweetReport tweetReport,String sexuality,String hateSpeech,String harassment,String dangerousContent);
    public Tweet saveTweet(Tweet tweet);

    public List<TweetReport> findAllTweetReports();

    public Tweet findTweetByReportId(Integer reportId);

    public  Users findUserByReportId(Integer reportId);

    public Tweet banTweet(Integer tweetId);


    public String addEmployeeToReport(Integer reportsId, Integer empId);

    Employee findEmployeeByReportId(Integer reportId);

    Tweet postTweetForShare(Integer userId, String content, String imgUrl);

    TweetData getLastTweetData();

    public String uploadOfficialImg(MultipartFile file);

    public String uploadTweetImgToCloud(MultipartFile file);

    public TweetOfficial saveOfficialTweet(TweetOfficial tweetOfficial);

    public List<TweetOfficial> findAllOfficialTweet();

    public TweetOfficial updateOfficialTweetContent(Integer tweetId, String newContent);

    public TweetOfficial findOfficialTweetByTweetId(Integer tweetId);

    public TweetOfficial saveTweetOfficial(TweetOfficial tweetOfficial);

    public List<TweetOfficial> findLast3TweetOfficial();
}
