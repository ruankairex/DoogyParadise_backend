package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TweetRepository extends JpaRepository<Tweet, Integer> {


    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.user.userId = ?1 AND t.preNode = 0 AND t.tweetStatus = 1 ORDER BY t.postDate DESC")
    List<Tweet> findTweetsByUserId(Integer userId);

    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.user.userId = ?1 AND t.tweetStatus = 1 ORDER BY t.postDate DESC")
    List<Tweet> findTweetsAndCommentsByUserId(Integer userId);
    //找所有推文與圖片
    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.tweetStatus = 1")
    List<Tweet> findAllTweetsWithGallery();


    @Query("FROM Tweet t ")
    List<Tweet> findAllTweetsOnly();

    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.preNode = ?1 AND t.tweetStatus = 1")
    List<Tweet> findCommentByPreNodeId(Integer preNodeId);

    //找出所有貼文與圖片(不包括留言)，且分頁
//    @Query("SELECT t FROM Tweet t LEFT JOIN FETCH t.tweetGalleries WHERE t.preNode = 0 AND t.tweetStatus = 1" )
//    Page<Tweet> findAllTweetsWithGallery(Pageable pageable);
    @Query("SELECT t FROM Tweet t LEFT JOIN FETCH t.tweetGalleries WHERE t.preNode = 0 AND t.tweetStatus = 1 ORDER BY t.postDate DESC")
    Page<Tweet> findAllTweetsWithGallery(Pageable pageable);

    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.user.userId = ?1 AND t.preNode = 0 AND t.tweetStatus = 1")
    List<Tweet> findTweetsWithGalleryWithNoComment(Integer userId);

    @Query("SELECT t FROM Tweet t LEFT JOIN t.tweetGalleries WHERE t.user.lastName = ?1 AND t.preNode = 0 AND t.tweetStatus = 1")
    List<Tweet> findTweetsByUserName(String userName);
    @Query("SELECT t FROM Tweet t LEFT JOIN  t.tweetGalleries WHERE t.tweetId = ?1 AND t.tweetStatus = 1")
    Tweet findTweetByTweetId(Integer tweetId);

    @Query("SELECT t FROM Tweet t LEFT JOIN  t.user WHERE t.tweetId = ?1")
    Tweet findTweetAndUserByTweetIdForEMP(Integer tweetId);

    @Query("SELECT u FROM Users u JOIN FETCH u.tweetLikes t WHERE t.tweetId = ?1")
    List<Users> findUserLikesByTweetId(Integer tweetId);

    @Query("SELECT t FROM Tweet t LEFT JOIN  t.userLikes WHERE t.tweetId = ?1 AND t.tweetStatus = 1")
    Tweet findTweetUserLikesByTweetId(Integer tweetId);

    @Query("SELECT t FROM Tweet t  JOIN FETCH t.dogs WHERE t.tweetId = ?1 AND t.tweetStatus = 1")
    Tweet findTweetAndDogsByTweetId(Integer tweetId);

    @Query("SELECT t FROM Tweet t  LEFT JOIN FETCH t.dogs WHERE t.tweetId = ?1 AND t.tweetStatus = 1")
    Tweet findTweetAndDogsByTweetIdByLEFTJOIN(Integer tweetId);


    @Query("SELECT t FROM Tweet t  LEFT JOIN  t.tweetReports r WHERE r.reportsId = ?1")
    Tweet findTweetAndReportsByTweetReportsId(Integer tweetReports);


    @Query("SELECT t FROM Tweet t LEFT JOIN FETCH t.tweetReports r WHERE t.tweetId = ?1")
    Tweet findTweetAndReportsByTweetId(Integer tweetId);

    @Query("SELECT t FROM Tweet t JOIN FETCH t.tweetReports ")
    List<Tweet> findAllTweetsHasReport();

    @Query("SELECT t FROM Tweet t JOIN FETCH t.tweetReports r WHERE r.reportsId = ?1")
    Tweet findTweetByReportId(Integer reportId);

}
