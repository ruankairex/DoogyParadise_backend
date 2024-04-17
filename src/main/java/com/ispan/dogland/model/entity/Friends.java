package com.ispan.dogland.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="friends")
public class Friends {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer iId;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Users user;

    @JsonManagedReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "friend_id")
    private Users friend;

    @Column(name = "become_friend_time")
    private Date becomeFriendTime;

    public Friends(Users user, Users friend, Date becomeFriendTime) {
        this.user = user;
        this.friend = friend;
        this.becomeFriendTime = becomeFriendTime;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Users getFriend() {
        return friend;
    }

    public void setFriend(Users friend) {
        this.friend = friend;
    }

    public Date getBecomeFriendTime() {
        return becomeFriendTime;
    }

    public void setBecomeFriendTime(Date becomeFriendTime) {
        this.becomeFriendTime = becomeFriendTime;
    }
}
