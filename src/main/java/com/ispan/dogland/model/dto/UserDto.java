package com.ispan.dogland.model.dto;

import com.ispan.dogland.model.entity.Users;

import java.util.Date;

public class UserDto {

    private Integer userId;

    private String lastName;

    private String userEmail;

    private String userPassword;

    private String userGender;

    private Date birthDate;

    private Integer userViolationCount;

    private Date lastLoginTime;

    private String userImgPath;

    private String imgPublicId;

    private String userStatus;

    public UserDto() {
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserGender() {
        return userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getUserViolationCount() {
        return userViolationCount;
    }

    public void setUserViolationCount(Integer userViolationCount) {
        this.userViolationCount = userViolationCount;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getUserImgPath() {
        return userImgPath;
    }

    public void setUserImgPath(String userImgPath) {
        this.userImgPath = userImgPath;
    }

    public String getImgPublicId() {
        return imgPublicId;
    }

    public void setImgPublicId(String imgPublicId) {
        this.imgPublicId = imgPublicId;
    }

    public String getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

    public void setUser(Users user){
        this.userId = user.getUserId();
        this.lastName = user.getLastName();
        this.userEmail = user.getUserEmail();
        this.userPassword = user.getUserPassword();
        this.userGender = user.getUserGender();
        this.birthDate = user.getBirthDate();
        this.userViolationCount = user.getUserViolationCount();
        this.lastLoginTime = user.getLastLoginTime();
        this.userImgPath = user.getUserImgPath();
        this.imgPublicId = user.getImgPublicId();
        this.userStatus = user.getUserStatus();
    }
    public void setUserWithOutPassword(Users user){
        this.userId = user.getUserId();
        this.lastName = user.getLastName();
        this.userEmail = user.getUserEmail();
        this.userGender = user.getUserGender();
        this.birthDate = user.getBirthDate();
        this.userViolationCount = user.getUserViolationCount();
        this.lastLoginTime = user.getLastLoginTime();
        this.userImgPath = user.getUserImgPath();
        this.userStatus = user.getUserStatus();
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("UserDto{");
        sb.append("userId=").append(userId);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", userEmail='").append(userEmail).append('\'');
        sb.append(", userPassword='").append(userPassword).append('\'');
        sb.append(", userGender='").append(userGender).append('\'');
        sb.append(", birthDate=").append(birthDate);
        sb.append(", userViolationCount=").append(userViolationCount);
        sb.append(", lastLoginTime=").append(lastLoginTime);
        sb.append(", userImgPath='").append(userImgPath).append('\'');
        sb.append(", imgPublicId='").append(imgPublicId).append('\'');
        sb.append(", userStatus='").append(userStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
