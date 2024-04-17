package com.ispan.dogland.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.tweet.TweetNotification;
import com.ispan.dogland.model.entity.tweet.TweetOfficial;
import com.ispan.dogland.model.entity.tweet.TweetReport;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    private String lastName;

    private String firstName;

    private String userEmail;

    private String userPassword;

    private String userGender;

    private Date birthDate;

    private Integer userViolationCount;

    private Date lastLoginTime;

    private String userImgPath;

    private String imgPublicId;

    private String userStatus;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Dog> dogs;

    @OneToMany(mappedBy = "users")
    private List<ShoppingCart> shoppingCarts;

    @OneToMany(mappedBy = "users")
    private List<Collection> collections;

    @OneToMany(mappedBy = "users")
    private List<Comment> comments;

    @OneToMany(mappedBy = "users")
    private List<Orders> orders;

//    @OneToMany(fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL,
//            mappedBy = "user")
//    private List<Friends> friends;

    @OneToMany(mappedBy = "user",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Tweet> tweets;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                                                    CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="tweet_like", joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="tweet_id"))
    private List<Tweet> tweetLikes;

    @OneToMany(mappedBy = "reporter",
                cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TweetReport> tweetReports;

    @OneToMany(mappedBy = "user",
            fetch = FetchType.LAZY ,
            cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<RoomReservation> roomReservation;

    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(lastLoginTime==null ) {
            lastLoginTime=new Date();
        }
    }

    public Users() {
    }
    //加了這個
    public Users(Integer userId){
        this.userId = userId;
    }

    public Users(String lastName, String firstName, String userEmail, String userPassword, String userGender, Date birthDate, Integer userViolationCount, Date lastLoginTime, String userStatus) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userGender = userGender;
        this.birthDate = birthDate;
        this.userViolationCount = userViolationCount;
        this.lastLoginTime = lastLoginTime;
        this.userStatus = userStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getUserViolationCount() {
        return userViolationCount;
    }

    public void setUserViolationCount(Integer userViolationCount) {
        this.userViolationCount = userViolationCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public List<Dog> getDogs() {
        return dogs;
    }

    public void setDogs(List<Dog> dogs) {
        this.dogs = dogs;
    }


    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getImgPublicId() {
        return imgPublicId;
    }

    public void setImgPublicId(String imgPublicId) {
        this.imgPublicId = imgPublicId;
    }

    public List<Tweet> getTweetLikes() {
        return tweetLikes;
    }

    public void setTweetLikes(List<Tweet> tweetLikes) {
        this.tweetLikes = tweetLikes;
    }

    public List<TweetReport> getTweetReports() {
        return tweetReports;
    }

    public void setTweetReports(List<TweetReport> tweetReports) {
        this.tweetReports = tweetReports;
    }

    public void addTweetLike(Tweet tweet) {
        if(tweetLikes == null) {
            tweetLikes = new ArrayList<>();
        }
        tweetLikes.add(tweet);
    }

    public void addTweetReport(TweetReport tweetReport) {
        if(tweetReports == null) {
            tweetReports = new ArrayList<>();
        }
        tweetReports.add(tweetReport);
        tweetReport.setReporter(this);
    }

    public void addDog(Dog dog) {
        if(this.dogs == null) {
            this.dogs = new ArrayList<>();
        }
        this.dogs.add(dog);
        dog.setUser(this);
    }

    public void addTweet(Tweet tweet) {
        if(this.tweets == null) {
            this.tweets = new ArrayList<>();
        }
        this.tweets.add(tweet);
        tweet.setUser(this);
    }

    public List<ShoppingCart> getShoppingCarts() {
        return shoppingCarts;
    }

    public void setShoppingCarts(List<ShoppingCart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }

    public List<Orders> getOrders() {
        return orders;
    }

    public void setOrders(List<Orders> orders) {
        this.orders = orders;
    }

    public List<Collection> getCollections() {
        return collections;
    }

    public void setCollections(List<Collection> collections) {
        this.collections = collections;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Users{");
        sb.append("userId=").append(userId);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", userPassword='").append(userPassword).append('\'');
        sb.append(", userGender='").append(userGender).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", userViolationCount=").append(userViolationCount);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", userImgPath='").append(userImgPath).append('\'');
        sb.append(", imgPublicId='").append(imgPublicId).append('\'');
        sb.append(", userStatus='").append(userStatus).append('\'');
//        sb.append(", dogs=").append(dogs);
        sb.append('}');
        return sb.toString();
    }


}
