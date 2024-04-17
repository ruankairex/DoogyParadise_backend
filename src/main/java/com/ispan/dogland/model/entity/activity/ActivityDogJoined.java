package com.ispan.dogland.model.entity.activity;

import com.ispan.dogland.model.entity.Dog;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
public class ActivityDogJoined {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dogJoinedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "activity_id")
    private VenueActivity venueActivity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dog_id")
    private Dog dog;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date joinedTime;

    @Column(columnDefinition = "VARCHAR(20) DEFAULT '參加'")//已取消
    private String joinedStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date updateTime;

    ////////////////////////
    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(joinedTime==null && updateTime==null) {
            joinedTime=new Date();
            updateTime=new Date();
            if(joinedStatus==null){
                joinedStatus="參加";
            }
        }
    }

    @PreUpdate
    public void onUpdate(){
        updateTime = new Date();
    }

    ///////////////////////

    public Integer getDogJoinedId() {
        return dogJoinedId;
    }

    public void setDogJoinedId(Integer dogJoinedId) {
        this.dogJoinedId = dogJoinedId;
    }

    public VenueActivity getVenueActivity() {
        return venueActivity;
    }

    public void setVenueActivity(VenueActivity venueActivity) {
        this.venueActivity = venueActivity;
    }

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public Date getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    public String getJoinedStatus() {
        return joinedStatus;
    }

    public void setJoinedStatus(String joinedStatus) {
        this.joinedStatus = joinedStatus;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
