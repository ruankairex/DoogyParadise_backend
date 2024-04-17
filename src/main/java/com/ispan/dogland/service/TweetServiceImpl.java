package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.tweet.*;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.mongodb.TweetData;
import com.ispan.dogland.model.entity.tweet.*;
import com.ispan.dogland.service.interfaceFile.TweetService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;

@Service
public class TweetServiceImpl implements TweetService {

    @Value("${imagepath}")
    private String uploadDir;
    private static final long MAX_FILE_SIZE_BYTES = 3 * 1024 * 1024; // 3MB

    private TweetRepository tweetRepository;
    private UserRepository userRepository;
    private TweetGalleryRepository tweetGalleryRepository;
    private TweetLikeRepository tweetLikeRepository;
    private TweetFollowListRepository tweetFollowListRepository;
    private DogRepository dogRepository;
    private TweetNotificationRepository tweetNotificationRepository;
    private TweetReportRepository tweetReportRepository;
    private EmployeeRepository employeeRepository;
    private TweetDataRepository tweetDataRepository;
    private Cloudinary cloudinary;
    private TweetOfficialRepository tweetOfficialRepository;

    @Autowired
    public TweetServiceImpl(TweetRepository tweetRepository,
                            UserRepository userRepository,
                            TweetGalleryRepository tweetGalleryRepository,
                            TweetLikeRepository tweetLikeRepository,
                            TweetFollowListRepository tweetFollowListRepository,
                            DogRepository dogRepository,
                            TweetNotificationRepository tweetNotificationRepository,
                            TweetReportRepository tweetReportRepository,
                            EmployeeRepository employeeRepository,
                            TweetDataRepository tweetDataRepository,
                            Cloudinary cloudinary,
                            TweetOfficialRepository tweetOfficialRepository) {
        this.tweetRepository = tweetRepository;
        this.userRepository = userRepository;
        this.tweetGalleryRepository = tweetGalleryRepository;
        this.tweetLikeRepository = tweetLikeRepository;
        this.tweetFollowListRepository = tweetFollowListRepository;
        this.dogRepository = dogRepository;
        this.tweetNotificationRepository = tweetNotificationRepository;
        this.tweetReportRepository = tweetReportRepository;
        this.employeeRepository = employeeRepository;
        this.tweetDataRepository = tweetDataRepository;
        this.cloudinary = cloudinary;
        this.tweetOfficialRepository = tweetOfficialRepository;
    }

    @Override
    public List<Tweet> findTweetsByUserId(Integer userId) {
        Users tmp = userRepository.findByUserId(userId);
        if (tmp != null) {
            return tweetRepository.findTweetsByUserId(userId);
        }
        return null;
    }

    @Override
    public Users findUserByTweetId(Integer tweetId) {
        return userRepository.findByTweetId(tweetId);
    }

    @Override
    public List<Tweet> findTweetsByUserName(String userName) {
        return tweetRepository.findTweetsByUserName(userName);
    }

    @Override
    public List<Tweet> getAllTweet() {
        List<Tweet> tweets = tweetRepository.findAllTweetsWithGallery();
        List<Tweet> ret = new ArrayList<>();
        for (Tweet tweet : tweets) {
            if (tweet.getPreNode() == 0) {
                ret.add(tweet);
            }
        }
        return ret;
    }

    @Override
    public Tweet postNewTweet(Tweet tweet, Integer userId) {
        Users user = userRepository.findByUserId(userId);
        if (user != null) {
            tweet.setUserName(user.getLastName());
            Tweet t = tweetRepository.save(tweet); //into DB
            t.setUser(user);
            Tweet t1 = tweetRepository.save(t);
            return t1;
        }
        return null;
    }

    public String saveTweetImgToLocal(MultipartFile file) {
        try {
            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            String fileName = timeStamp + "_" + originalFileName;
            File uploadPath = new File(uploadDir);
            if (!uploadPath.exists()) {
                uploadPath.mkdirs();
            }
            Path filePath = Paths.get(uploadDir, fileName);

            // 如果大於3MB就壓縮
            if (file.getSize() > MAX_FILE_SIZE_BYTES) {
                BufferedImage originalImage = ImageIO.read(file.getInputStream());
                BufferedImage resizedImage = resizeImage(originalImage);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(resizedImage, "jpg", baos);
                ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
                FileUtils.copyInputStreamToFile(bais, filePath.toFile());
            } else {
                Files.copy(file.getInputStream(), filePath);
            }

            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private BufferedImage resizeImage(BufferedImage originalImage) {
        System.out.println("壓縮圖片...........");
        int targetWidth = originalImage.getWidth();
        int targetHeight = originalImage.getHeight();
        while (calculateSize(originalImage) > MAX_FILE_SIZE_BYTES) {
            targetWidth *= 0.9;
            targetHeight *= 0.9;
        }

        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = resizedImage.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();

        return resizedImage;
    }

    private long calculateSize(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return baos.toByteArray().length;
    }

    @Override
    public List<Tweet> getNumOfComment(Integer tweetId) {
        return tweetRepository.findCommentByPreNodeId(tweetId);
    }

    @Override
    public List<Tweet> getAllTweetForPage(int page, int limit) {
        Page<Tweet> tweetPage = tweetRepository.findAllTweetsWithGallery(PageRequest.of(page - 1, limit));
        return tweetPage.getContent();
    }

    @Override
    public List<Users> findUserLikesByTweetId(Integer tweetId) {
        return tweetRepository.findUserLikesByTweetId(tweetId);
    }


    @Override
    public void createLinkWithTweetAndLike(Integer tweetId, Integer userId) {
        Users user = userRepository.findByUserId(userId);
        Tweet tweet = tweetRepository.findTweetUserLikesByTweetId(tweetId);
        tweet.addUserLike(user);
        tweetRepository.save(tweet);
    }

    @Override
    public void removeLinkWithTweetAndLike(Integer tweetId, Integer userId) {
        TweetLike ll = tweetLikeRepository.findByTweetIdAndUserId(tweetId, userId);
        if (ll != null) {
            tweetLikeRepository.delete(ll);
        }
    }

    @Override
    public Tweet findTweetByTweetId(Integer tweetId) {
        return tweetRepository.findTweetByTweetId(tweetId);
    }

    @Override
    public boolean checkIsFollow(Integer myUserId, Integer otherUserId) {
        TweetFollowList tmp = tweetFollowListRepository.findByMyUserIdAndOtherUserId(myUserId, otherUserId);
        return tmp != null;
    }
    @Override
    public boolean followTweetUser(Integer myUserId, Integer otherUserId) {
        Users myUser = userRepository.findByUserId(myUserId);
        Users otherUser = userRepository.findByUserId(otherUserId);
        if (myUser != null && otherUser != null) {
            TweetFollowList tmp = new TweetFollowList(myUserId, otherUserId);
            tweetFollowListRepository.save(tmp);
            return true;
        }
        return false;
    }

    @Override
    public void deleteFollowTweetUser(Integer myUserId, Integer otherUserId) {
        tweetFollowListRepository.deleteByUserIdAndFollwerId(myUserId, otherUserId);
    }

    @Override
    public List<Tweet> findAllFollowTweetsByUserId(Integer userId) {
        //該user所有追蹤的人
        List<TweetFollowList> followList = tweetFollowListRepository.findByUserId(userId);
        List<Tweet> totalTweet = new ArrayList<>();
        //把每一個追蹤的人的tweets找出來
        for (TweetFollowList tmp : followList) {
            totalTweet.addAll(tweetRepository.findTweetsWithGalleryWithNoComment(tmp.getFollwerId()));
        }
        return totalTweet;
    }

    @Override
    public List<Users> findAllFollowUsersByUserId(Integer userId) {
        //該user所有追蹤的人
        List<TweetFollowList> followList = tweetFollowListRepository.findByUserId(userId);
        List<Users> totalUsers = new ArrayList<>();
        for (TweetFollowList tmp : followList) {
            totalUsers.add(userRepository.findByUserId(tmp.getFollwerId()));
        }
        return totalUsers;
    }

    @Override
    public List<Dog> findTweetDogsByTweetId(Integer tweetId) {
        Tweet a = tweetRepository.findTweetAndDogsByTweetId(tweetId);
        if(a != null){
            return a.getDogs();
        }
        return null;
    }

    @Override
    public Tweet addDogToTweet(Integer dogId, Integer tweetId) {
        Tweet b = tweetRepository.findTweetAndDogsByTweetIdByLEFTJOIN(tweetId);
        Dog c = dogRepository.findByDogId(dogId);
        b.addDog(c);
        return tweetRepository.save(b);
    }

    @Override
    public Tweet removeDogFromTweet(Integer dogId, Integer tweetId) {
        Tweet b = tweetRepository.findTweetAndDogsByTweetId(tweetId);
        if(b != null){
            List<Dog> dogs = b.getDogs();
            for(Dog d : dogs){
                if(Objects.equals(d.getDogId(), dogId)){
                    dogs.remove(d);
                    tweetRepository.save(b);
                    return b;
                }
            }
        }
        return null;
    }

    @Override
    public void sendPostTweetNotificationToFollower(Integer userId, Integer tweetId) {
        Users user = userRepository.findByUserId(userId);
        String userName = user.getLastName();

        List<TweetFollowList> li = tweetFollowListRepository.findByUserId(userId);

        for(TweetFollowList t:li){
            TweetNotification tweetNotification = new TweetNotification();

            tweetNotification.setPostTime(new Date());
            tweetNotification.setUserId(t.getFollwerId());
            tweetNotification.setContent(userName+"發布新推文囉~快去看看!!!");
            tweetNotification.setIsRead(0);
            tweetNotification.setTweetId(tweetId);
            tweetNotificationRepository.save(tweetNotification);
        }

    }

    @Override
    public void sendReplyNotificationToTweetOwner(Integer hisUserId, Integer hisTweetId,String myName) {
        TweetNotification tweetNotification = new TweetNotification();

        tweetNotification.setPostTime(new Date());
        tweetNotification.setUserId(hisUserId);
        tweetNotification.setContent(myName+"回覆你的推文囉~ 快去看看!!!");
        tweetNotification.setIsRead(0);
        tweetNotification.setTweetId(hisTweetId);
        tweetNotificationRepository.save(tweetNotification);
    }

    @Override
    public void sendLikeNotificationToTweetOwner(Integer hisUserId, Integer hisTweetId, String myName) {
        List<TweetNotification> tnfs = tweetNotificationRepository.findByUserIdAndTweetId(hisUserId, hisTweetId);

        if(tnfs!=null){
            for(TweetNotification tnf:tnfs){
                String notificationContent = tnf.getContent();
                if(notificationContent.equals(myName+"對你的貼文看讚~ 快去看看!!!")){
                    return;
                }
            }
        }
        TweetNotification tweetNotification = new TweetNotification();

        tweetNotification.setPostTime(new Date());
        tweetNotification.setUserId(hisUserId);
        tweetNotification.setContent(myName+"對你的貼文看讚~ 快去看看!!!");
        tweetNotification.setIsRead(0);
        tweetNotification.setTweetId(hisTweetId);
        tweetNotificationRepository.save(tweetNotification);
    }

    @Override
    public void sendBanTweetNotificationToUser(Integer userId,Tweet tweet) {
        TweetNotification tweetNotification = new TweetNotification();

        tweetNotification.setPostTime(new Date());
        tweetNotification.setUserId(userId);
        tweetNotification.setContent("您的一則貼文因違反平台政策，已被移除。\n推文內容：" + tweet.getTweetContent());
        tweetNotification.setIsRead(0);
        tweetNotificationRepository.save(tweetNotification);
    }


    @Override
    public List<TweetNotification> findMyTweetNotifications(Integer userId) {

        List<TweetNotification> notifications = tweetNotificationRepository.findByUserId(userId);
        // 使用 Collections.sort()
        Collections.sort(notifications, new Comparator<TweetNotification>() {
            @Override
            public int compare(TweetNotification notification1, TweetNotification notification2) {
                // postTime 排序
                return notification2.getPostTime().compareTo(notification1.getPostTime());
            }
        });
        List<TweetNotification> top15Notifications = notifications.subList(0, Math.min(5, notifications.size()));
        return top15Notifications;
    }

    @Override
    public List<Tweet> findTweetsAndCommentsByUserId(Integer userId) {
        return tweetRepository.findTweetsAndCommentsByUserId(userId);
    }

    @Override
    public List<Tweet> findAllTweetsOnly() {
        return tweetRepository.findAllTweetsOnly();
    }

    @Override
    public TweetNotification findTweetNotificationByNotifiId(Integer id) {
        return tweetNotificationRepository.findByTweetNotiId(id);
    }

    @Override
    public void saveTweetNotification(TweetNotification t1) {
        tweetNotificationRepository.save(t1);
    }

    @Override
    public Tweet updateTweetContent(Integer tweetId, String newContent) {
        Tweet t1 = tweetRepository.findTweetByTweetId(tweetId);
        if(t1 == null){
            return null;
        }
        t1.setTweetContent(newContent);
        return tweetRepository.save(t1);
    }

    @Override
    public boolean checkUserAndReportRelation(Integer tweetId, Integer userId) {
        TweetReport tr = tweetReportRepository.findByTweetIdAndUserId(tweetId, userId);
        return tr != null;
    }

    @Override
    public TweetReport addReportToTweet(Integer tweetId, Integer userId,String reportText, String reportCheckBox) {


        TweetReport tweetReportTmp = new TweetReport();
        tweetReportTmp.setReportReason(reportCheckBox);
        tweetReportTmp.setReportDescription(reportText);
        tweetReportTmp.setReportDate(new Date());
        tweetReportTmp.setReportStatus(0);
        TweetReport tweetReport = tweetReportRepository.save(tweetReportTmp);

        Users user= userRepository.findUserAndReportsByUserId(userId);
        user.addTweetReport(tweetReport);
        userRepository.save(user);

        Tweet tweet = tweetRepository.findTweetAndReportsByTweetId(tweetId);
        Integer numReport = tweet.getNumReport();
        tweet.setNumReport(numReport+1);
        tweet.addTweetReport(tweetReport);
        tweetRepository.save(tweet);

        return tweetReport;

    }

    @Override
    public TweetReport addAiReportToTweet(TweetReport tweetReport,String sexuality, String hateSpeech, String harassment, String dangerousContent) {
        tweetReport.setSexuallyExplicit(sexuality);
        tweetReport.setHateSpeech(hateSpeech);
        tweetReport.setHarassment(harassment);
        tweetReport.setDangerousContent(dangerousContent);
        return tweetReportRepository.save(tweetReport);
    }

    @Override
    public Tweet saveTweet(Tweet tweet) {
        return tweetRepository.save(tweet);
    }

    @Override
    public List<TweetReport> findAllTweetReports() {
        return tweetReportRepository.findAll();
    }

    @Override
    public Tweet findTweetByReportId(Integer reportId) {
        return tweetRepository.findTweetByReportId(reportId);
    }

    @Override
    public Users findUserByReportId(Integer reportId) {
        return userRepository.findUserByReportId(reportId);
    }

    @Override
    public Tweet banTweet(Integer tweetId) {
        Tweet t1 = tweetRepository.findTweetAndUserByTweetIdForEMP(tweetId);
        if(t1 == null){
            return null;
        }
        t1.setTweetStatus(0);
        return tweetRepository.save(t1);
    }


    @Override
    public String addEmployeeToReport(Integer reportsId, Integer empId) {
        Employee emp = employeeRepository.findEmployeeAndReportsByEmployeeId(empId);
        TweetReport tr = tweetReportRepository.findByTweetReportId(reportsId);
        tr.setReportStatus(1);
        emp.addTweetReport(tr);
        employeeRepository.save(emp);
        return null;
    }

    @Override
    public Employee findEmployeeByReportId(Integer reportId) {
        return employeeRepository.findEmployeeByTweetReportId(reportId);
    }

    @Override
    public Tweet postTweetForShare(Integer userId,String content, String imgUrl) {
        //tweet
        TweetGallery tweetGallery = new TweetGallery();
        tweetGallery.setImgPath(imgUrl);
        Tweet tweet = new Tweet();
        tweet.setTweetContent(content);
        tweet.setPreNode(0);
        tweet.setPostDate(new Date());
        tweet.setTweetStatus(1);
        tweet.setNumReport(0);
        tweet.addTweetGallery(tweetGallery);
        Tweet tweetInDb = tweetRepository.save(tweet);
        Tweet tweetForReturn = this.postNewTweet(tweetInDb, userId);
        return tweetForReturn;
    }

    @Override
    public TweetData getLastTweetData() {
        List<TweetData> tweetData = tweetDataRepository.findAllByOrderByTimestampDesc();
        return tweetData.get(0);

    }

    @Override
    public String uploadOfficialImg(MultipartFile file) {
        try{
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "tweetOfficialFolder"));
            return (String) data.get("url");
        }catch (IOException e){
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    @Override
    public String uploadTweetImgToCloud(MultipartFile file) {
        try{
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "tweetFolder"));
            return (String) data.get("url");
        }catch (IOException e){
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    @Override
    public TweetOfficial saveOfficialTweet(TweetOfficial tweetOfficial) {
        return tweetOfficialRepository.save(tweetOfficial);
    }

    @Override
    public List<TweetOfficial> findAllOfficialTweet() {
        return tweetOfficialRepository.findAllOfficialTweetWhereStatusIs1();
    }

    @Override
    public TweetOfficial updateOfficialTweetContent(Integer tweetId, String newContent) {

        TweetOfficial t1 = tweetOfficialRepository.findByTweetId(tweetId);
        if(t1 == null){
            return null;
        }
        t1.setTweetContent(newContent);
        return tweetOfficialRepository.save(t1);
    }

    @Override
    public TweetOfficial findOfficialTweetByTweetId(Integer tweetId) {
        return tweetOfficialRepository.findByTweetId(tweetId);
    }

    @Override
    public TweetOfficial saveTweetOfficial(TweetOfficial tweetOfficial) {
        return tweetOfficialRepository.save(tweetOfficial);
    }

    @Override
    public List<TweetOfficial> findLast3TweetOfficial() {
        return tweetOfficialRepository.findLast3();
    }


}
