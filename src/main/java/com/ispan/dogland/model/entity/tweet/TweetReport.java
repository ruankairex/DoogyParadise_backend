package com.ispan.dogland.model.entity.tweet;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Tweet_reports")
public class TweetReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reports_id")
    private Integer reportsId;

    @ManyToOne
    @JoinColumn(name = "tweet_id")
    @JsonIgnore
    private Tweet tweet;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    @JsonIgnore
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "reporter_id")
    @JsonIgnore
    private Users reporter;

    private String reportReason;

    private String reportDescription;

    private Date reportDate;

    private Integer reportStatus;

    private String sexuallyExplicit;
    private String hateSpeech;
    private String harassment;
    private String dangerousContent;

    public TweetReport() {
    }

    public TweetReport(Tweet tweet, Employee employee, Users reporter, String reportReason, String reportDescription, Date reportDate, Integer reportStatus) {
        this.tweet = tweet;
        this.employee = employee;
        this.reporter = reporter;
        this.reportReason = reportReason;
        this.reportDescription = reportDescription;
        this.reportDate = reportDate;
        this.reportStatus = reportStatus;
    }

    public Integer getReportsId() {
        return reportsId;
    }

    public void setReportsId(Integer reportsId) {
        this.reportsId = reportsId;
    }

    public Tweet getTweet() {
        return tweet;
    }

    public void setTweet(Tweet tweet) {
        this.tweet = tweet;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Users getReporter() {
        return reporter;
    }

    public void setReporter(Users reporter) {
        this.reporter = reporter;
    }

    public String getReportReason() {
        return reportReason;
    }

    public void setReportReason(String reportReason) {
        this.reportReason = reportReason;
    }

    public String getReportDescription() {
        return reportDescription;
    }

    public void setReportDescription(String reportDescription) {
        this.reportDescription = reportDescription;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public Integer getReportStatus() {
        return reportStatus;
    }

    public void setReportStatus(Integer reportStatus) {
        this.reportStatus = reportStatus;
    }


    public String getSexuallyExplicit() {
        return sexuallyExplicit;
    }

    public void setSexuallyExplicit(String sexuallyExplicit) {
        this.sexuallyExplicit = sexuallyExplicit;
    }

    public String getHateSpeech() {
        return hateSpeech;
    }

    public void setHateSpeech(String hateSpeech) {
        this.hateSpeech = hateSpeech;
    }

    public String getHarassment() {
        return harassment;
    }

    public void setHarassment(String harassment) {
        this.harassment = harassment;
    }

    public String getDangerousContent() {
        return dangerousContent;
    }

    public void setDangerousContent(String dangerousContent) {
        this.dangerousContent = dangerousContent;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TweetReport{");
        sb.append("reportsId=").append(reportsId);
        sb.append(", tweet=").append(tweet);
        sb.append(", employee=").append(employee);
        sb.append(", reporter=").append(reporter);
        sb.append(", reportReason='").append(reportReason).append('\'');
        sb.append(", reportDescription='").append(reportDescription).append('\'');
        sb.append(", reportDate=").append(reportDate);
        sb.append(", reportStatus=").append(reportStatus);
        sb.append(", sexuallyExplicit='").append(sexuallyExplicit).append('\'');
        sb.append(", hateSpeech='").append(hateSpeech).append('\'');
        sb.append(", harassment='").append(harassment).append('\'');
        sb.append(", dangerousContent='").append(dangerousContent).append('\'');
        sb.append('}');
        return sb.toString();
    }
}