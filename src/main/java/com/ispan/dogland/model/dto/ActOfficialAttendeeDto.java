package com.ispan.dogland.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ActOfficialAttendeeDto {
    ///userId////
    private Integer userId;
    private String firstName;
    ////userJoined///
    private Integer userJoinedId;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date joinedTime;
    private String joinedStatus;
    private String userNote;
    ///activity////
    private Integer activityId;
    ///dog///
    private List<String> dogNameList;
    private List<String> dogProfileList;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getUserJoinedId() {
        return userJoinedId;
    }

    public void setUserJoinedId(Integer userJoinedId) {
        this.userJoinedId = userJoinedId;
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

    public String getUserNote() {
        return userNote;
    }

    public void setUserNote(String userNote) {
        this.userNote = userNote;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public List<String> getDogNameList() {
        return dogNameList;
    }

    public void setDogNameList(List<String> dogNameList) {
        this.dogNameList = dogNameList;
    }

    public List<String> getDogProfileList() {
        return dogProfileList;
    }

    public void setDogProfileList(List<String> dogProfileList) {
        this.dogProfileList = dogProfileList;
    }
}

