package com.ispan.dogland.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ActivityBrief {
    private Integer activityId;
    private String activityTypeName;
    private String venueName;
    private String activityTitle;
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date activityDate;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date activityStart;
    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date activityEnd;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date activityUpdateDate;

    private String activityStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date activityClosingDate;
    private Integer activityDogNumber;
    private Integer currentDogNumber;

    private Integer currentUserNumber;
    private Integer activityCost;
    private String activityCostDescription;
    private String galleryImgUrl;
    private Integer likedTime;

    public Integer getLikedTime() {
        return likedTime;
    }

    public void setLikedTime(Integer likedTime) {
        this.likedTime = likedTime;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
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

    public Date getActivityStart() {
        return activityStart;
    }

    public void setActivityStart(Date activityStart) {
        this.activityStart = activityStart;
    }

    public Date getActivityEnd() {
        return activityEnd;
    }

    public void setActivityEnd(Date activityEnd) {
        this.activityEnd = activityEnd;
    }

    public Date getActivityUpdateDate() {
        return activityUpdateDate;
    }

    public void setActivityUpdateDate(Date activityUpdateDate) {
        this.activityUpdateDate = activityUpdateDate;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public Date getActivityClosingDate() {
        return activityClosingDate;
    }

    public void setActivityClosingDate(Date activityClosingDate) {
        this.activityClosingDate = activityClosingDate;
    }

    public Integer getCurrentDogNumber() {
        return currentDogNumber;
    }

    public void setCurrentDogNumber(Integer currentDogNumber) {
        this.currentDogNumber = currentDogNumber;
    }

    public String getGalleryImgUrl() {
        return galleryImgUrl;
    }

    public void setGalleryImgUrl(String galleryImgUrl) {
        this.galleryImgUrl = galleryImgUrl;
    }

    public Integer getCurrentUserNumber() {
        return currentUserNumber;
    }

    public void setCurrentUserNumber(Integer currentUserNumber) {
        this.currentUserNumber = currentUserNumber;
    }

    public Integer getActivityDogNumber() {
        return activityDogNumber;
    }

    public void setActivityDogNumber(Integer activityDogNumber) {
        this.activityDogNumber = activityDogNumber;
    }

    public Integer getActivityCost() {
        return activityCost;
    }

    public void setActivityCost(Integer activityCost) {
        this.activityCost = activityCost;
    }

    public String getActivityCostDescription() {
        return activityCostDescription;
    }

    public void setActivityCostDescription(String activityCostDescription) {
        this.activityCostDescription = activityCostDescription;
    }
}
