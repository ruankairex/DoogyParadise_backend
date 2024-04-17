package com.ispan.dogland.model.entity;

import com.ispan.dogland.model.entity.tweet.TweetGallery;
import com.ispan.dogland.model.entity.tweet.TweetReport;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Table
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Integer employeeId;

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;

    @Temporal(TemporalType.TIMESTAMP)
    private Date hireDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date birthDate;

    private String city;
    private String district;
    private String address;
    private String dbAuthority;

    @OneToMany(fetch = FetchType.LAZY,
                mappedBy = "employee",
                cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                           CascadeType.DETACH, CascadeType.REFRESH})
    private List<TweetReport> tweetReports;

    public Employee(){
    }

    public Integer getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Integer employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDbAuthority() {
        return dbAuthority;
    }

    public void setDbAuthority(String dbAuthority) {
        this.dbAuthority = dbAuthority;
    }

    public List<TweetReport> getTweetReports() {
        return tweetReports;
    }

    public void setTweetReports(List<TweetReport> tweetReports) {
        this.tweetReports = tweetReports;
    }

    public void addTweetReport(TweetReport tweetReport) {
        if(this.tweetReports == null) {
            this.tweetReports = new ArrayList<>();
        }
        this.tweetReports.add(tweetReport);
        tweetReport.setEmployee(this);
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Employee{");
        sb.append("employeeId=").append(employeeId);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", hireDate=").append(hireDate);
        sb.append(", birthDate=").append(birthDate);
        sb.append(", city='").append(city).append('\'');
        sb.append(", district='").append(district).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", dbAuthority='").append(dbAuthority).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
