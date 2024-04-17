package com.ispan.dogland.model.entity.activity;

import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Entity
@Table
public class LikedActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int likedActivityId;

    @ManyToOne
    @JoinColumn(name = "activity_id")
    private VenueActivity venueActivity;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date likedTime;

    ////////////////////////////
    @PostLoad
    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(likedTime==null) {
            likedTime=new Date();
        }
    }
    /////////////////////////////

    public int getLikedActivityId() {
        return likedActivityId;
    }

    public void setLikedActivityId(int likedActivityId) {
        this.likedActivityId = likedActivityId;
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

    public Date getLikedTime() {
        return likedTime;
    }

    public void setLikedTime(Date likedTime) {
        this.likedTime = likedTime;
    }
}
