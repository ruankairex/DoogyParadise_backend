package com.ispan.dogland.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.List;

public class ActivityShowInfo {
    //=============type=============
    private String activityTypeName;
    //=============venue=============
    private Integer venueId;
    private String venueName;
    //=============gallery=============
    private List<ActivityGalleryDto> activityImgList;
    //=============activity=============
    private Integer activityId;
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
    private String activityDescription;
    private String activityProcess;
    private String activityNotice;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date activityListedDate;
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
    private String contactInfo;
    private String contactMail;
    private String contactPhone;
    private Integer likedTime;

    public Integer getLikedTime() {
        return likedTime;
    }

    public void setLikedTime(Integer likedTime) {
        this.likedTime = likedTime;
    }

    public String getActivityTypeName() {
        return activityTypeName;
    }

    public void setActivityTypeName(String activityTypeName) {
        this.activityTypeName = activityTypeName;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public List<ActivityGalleryDto> getActivityImgList() {
        return activityImgList;
    }

    public void setActivityImgList(List<ActivityGalleryDto> activityImgList) {
        this.activityImgList = activityImgList;
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

    public String getActivityDescription() {
        return activityDescription;
    }

    public void setActivityDescription(String activityDescription) {
        this.activityDescription = activityDescription;
    }

    public String getActivityProcess() {
        return activityProcess;
    }

    public void setActivityProcess(String activityProcess) {
        this.activityProcess = activityProcess;
    }

    public String getActivityNotice() {
        return activityNotice;
    }

    public void setActivityNotice(String activityNotice) {
        this.activityNotice = activityNotice;
    }

    public Date getActivityListedDate() {
        return activityListedDate;
    }

    public void setActivityListedDate(Date activityListedDate) {
        this.activityListedDate = activityListedDate;
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

    public Integer getActivityDogNumber() {
        return activityDogNumber;
    }

    public void setActivityDogNumber(Integer activityDogNumber) {
        this.activityDogNumber = activityDogNumber;
    }

    public Integer getCurrentDogNumber() {
        return currentDogNumber;
    }

    public void setCurrentDogNumber(Integer currentDogNumber) {
        this.currentDogNumber = currentDogNumber;
    }

    public Integer getCurrentUserNumber() {
        return currentUserNumber;
    }

    public void setCurrentUserNumber(Integer currentUserNumber) {
        this.currentUserNumber = currentUserNumber;
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

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getContactMail() {
        return contactMail;
    }

    public void setContactMail(String contactMail) {
        this.contactMail = contactMail;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
}
