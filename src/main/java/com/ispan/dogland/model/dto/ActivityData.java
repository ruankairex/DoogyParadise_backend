package com.ispan.dogland.model.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class ActivityData {
    private Integer activityId;
    private Integer activityTypeId;
    private Integer venueId;
    private Integer employeeId;
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

    public ActivityData() {
    }

    public ActivityData(Integer activityId, Integer activityTypeId, Integer venueId, Integer employeeId, String activityTitle, Date activityDate, Date activityStart, Date activityEnd, String activityDescription, String activityProcess, String activityNotice, Date activityListedDate, Date activityUpdateDate, String activityStatus, Date activityClosingDate, Integer activityDogNumber, Integer currentDogNumber, Integer activityCost, String activityCostDescription, String contactInfo, String contactMail, String contactPhone) {
        this.activityId = activityId;
        this.activityTypeId = activityTypeId;
        this.venueId = venueId;
        this.employeeId = employeeId;
        this.activityTitle = activityTitle;
        this.activityDate = activityDate;
        this.activityStart = activityStart;
        this.activityEnd = activityEnd;
        this.activityDescription = activityDescription;
        this.activityProcess = activityProcess;
        this.activityNotice = activityNotice;
        this.activityListedDate = activityListedDate;
        this.activityUpdateDate = activityUpdateDate;
        this.activityStatus = activityStatus;
        this.activityClosingDate = activityClosingDate;
        this.activityDogNumber = activityDogNumber;
        this.currentDogNumber = currentDogNumber;
        this.activityCost = activityCost;
        this.activityCostDescription = activityCostDescription;
        this.contactInfo = contactInfo;
        this.contactMail = contactMail;
        this.contactPhone = contactPhone;
    }

    public ActivityData(Integer activityTypeId, Integer venueId, Integer employeeId, String activityTitle, Date activityDate, Date activityStart, Date activityEnd, String activityDescription, String activityProcess, String activityNotice, Date activityClosingDate, Integer activityDogNumber, Integer activityCost, String activityCostDescription, String contactInfo, String contactMail, String contactPhone) {
        this.activityTypeId = activityTypeId;
        this.venueId = venueId;
        this.employeeId = employeeId;
        this.activityTitle = activityTitle;
        this.activityDate = activityDate;
        this.activityStart = activityStart;
        this.activityEnd = activityEnd;
        this.activityDescription = activityDescription;
        this.activityProcess = activityProcess;
        this.activityNotice = activityNotice;
        this.activityClosingDate = activityClosingDate;
        this.activityDogNumber = activityDogNumber;
        this.activityCost = activityCost;
        this.activityCostDescription = activityCostDescription;
        this.contactInfo = contactInfo;
        this.contactMail = contactMail;
        this.contactPhone = contactPhone;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getActivityTypeId() {
        return activityTypeId;
    }

    public void setActivityTypeId(Integer activityTypeId) {
        this.activityTypeId = activityTypeId;
    }

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
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

    public Integer getCurrentUserNumber() {
        return currentUserNumber;
    }

    public void setCurrentUserNumber(Integer currentUserNumber) {
        this.currentUserNumber = currentUserNumber;
    }
}
