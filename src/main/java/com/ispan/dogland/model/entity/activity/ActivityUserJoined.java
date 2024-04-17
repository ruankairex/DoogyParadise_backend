package com.ispan.dogland.model.entity.activity;

import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
public class ActivityUserJoined {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userJoinedId;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private VenueActivity venueActivity;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date joinedTime;
    private String userNote;
    @Column(columnDefinition = "VARCHAR(20) DEFAULT '參加'")
    private String joinedStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date updateTime;

    //////////////////////////////

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

    /////////////////////////////

    public int getUserJoinedId() {
        return userJoinedId;
    }

    public void setUserJoinedId(int userJoinedId) {
        this.userJoinedId = userJoinedId;
    }

    public VenueActivity getVenueActivity() {
        return venueActivity;
    }

    public void setVenueActivity(VenueActivity venueActivity) {
        this.venueActivity = venueActivity;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Date getJoinedTime() {
        return joinedTime;
    }

    public void setJoinedTime(Date joinedTime) {
        this.joinedTime = joinedTime;
    }

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
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
