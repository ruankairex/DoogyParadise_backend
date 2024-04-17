package com.ispan.dogland.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ApplyData {
    private Integer userId;
    private Integer userJoinedId;
    private Integer dogJoinedId;
    private Integer activityId;
    private String activityTitle;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date activityDate;
    private String dogName;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getUserJoinedId() {
        return userJoinedId;
    }

    public void setUserJoinedId(Integer userJoinedId) {
        this.userJoinedId = userJoinedId;
    }

    public Integer getDogJoinedId() {
        return dogJoinedId;
    }

    public void setDogJoinedId(Integer dogJoinedId) {
        this.dogJoinedId = dogJoinedId;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }
}
