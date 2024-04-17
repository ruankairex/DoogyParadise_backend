package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.model.entity.room.RoomReservation;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "dog")
public class Dog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dogId;

    private String dogName;

    private String dogImgPathLocal;

    private String dogImgPathCloud;

    private String dogImgPublicId;

    private String dogGender;

    private String dogIntroduce;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "dog_birth_date")
    private Date dogBirthDate;

    private Double dogWeight;

    private String dogBreed;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private Users user;

    @OneToMany(mappedBy = "dog",
            fetch = FetchType.LAZY ,
            cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<RoomReservation> roomReservation;


    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(name="tweet_tag_dog", joinColumns = @JoinColumn(name="dog_id"),
            inverseJoinColumns = @JoinColumn(name="tweet_id"))
    @JsonIgnore
    private List<Tweet> tweets;


    public List<RoomReservation> getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(List<RoomReservation> roomReservation) {
        this.roomReservation = roomReservation;
    }

    public void add(RoomReservation tmpRoomReservation){
        if(roomReservation==null){
            roomReservation = new ArrayList<>();
        }
        roomReservation.add(tmpRoomReservation);

        tmpRoomReservation.setDog(this);
    }

    public Dog() {
    }

    public Dog(String dogName, String dogImgPathLocal, String dogImgPathCloud, String dogImgPublicId, String dogGender, String dogIntroduce, Date dogBirthDate, Double dogWeight, String dogBreed, Users user) {
        this.dogName = dogName;
        this.dogImgPathLocal = dogImgPathLocal;
        this.dogImgPathCloud = dogImgPathCloud;
        this.dogImgPublicId = dogImgPublicId;
        this.dogGender = dogGender;
        this.dogIntroduce = dogIntroduce;
        this.dogBirthDate = dogBirthDate;
        this.dogWeight = dogWeight;
        this.dogBreed = dogBreed;
        this.user = user;
    }

    public Integer getDogId() {
        return dogId;
    }

    public void setDogId(Integer dogId) {
        this.dogId = dogId;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogImgPathLocal() {
        return dogImgPathLocal;
    }

    public void setDogImgPathLocal(String dogImgPathLocal) {
        this.dogImgPathLocal = dogImgPathLocal;
    }

    public String getDogImgPathCloud() {
        return dogImgPathCloud;
    }

    public void setDogImgPathCloud(String dogImgPathCloud) {
        this.dogImgPathCloud = dogImgPathCloud;
    }

    public String getDogImgPublicId() {
        return dogImgPublicId;
    }

    public void setDogImgPublicId(String dogImgPublicId) {
        this.dogImgPublicId = dogImgPublicId;
    }

    public String getDogGender() {
        return dogGender;
    }

    public void setDogGender(String dogGender) {
        this.dogGender = dogGender;
    }

    public String getDogIntroduce() {
        return dogIntroduce;
    }

    public void setDogIntroduce(String dogIntroduce) {
        this.dogIntroduce = dogIntroduce;
    }

    public Date getDogBirthDate() {
        return dogBirthDate;
    }

    public void setDogBirthDate(Date dogBirthDate) {
        this.dogBirthDate = dogBirthDate;
    }

    public Double getDogWeight() {
        return dogWeight;
    }

    public void setDogWeight(Double dogWeight) {
        this.dogWeight = dogWeight;
    }

    public String getDogBreed() {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) {
        this.dogBreed = dogBreed;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public List<Tweet> getTweets() {
        return tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        this.tweets = tweets;
    }

    public void addTweet(Tweet tweet){
        if(tweets==null){
            tweets = new ArrayList<>();
        }
        tweets.add(tweet);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Dog{");
        sb.append("dogId=").append(dogId);
        sb.append(", dogName='").append(dogName).append('\'');
        sb.append(", dogImgPathLocal='").append(dogImgPathLocal).append('\'');
        sb.append(", dogImgPathCloud='").append(dogImgPathCloud).append('\'');
        sb.append(", dogImgPublicId='").append(dogImgPublicId).append('\'');
        sb.append(", dogGender='").append(dogGender).append('\'');
        sb.append(", dogIntroduce='").append(dogIntroduce).append('\'');
        sb.append(", dogBirthDate=").append(dogBirthDate);
        sb.append(", dogSize=").append(dogWeight);
        sb.append(", dogBreed='").append(dogBreed).append('\'');
        sb.append(", user=").append(user);
        sb.append('}');
        return sb.toString();
    }

}
