package com.ispan.dogland.model.entity.activity;

import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Table
@Entity
public class VenueRental {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer rentalId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="venue_id")
    private Venue venue;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private Users user;
    private Integer participantsNumber;
    private Integer dogNumber;

    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private Date rentalDate;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date rentalStart;

    @Temporal(TemporalType.TIME)
    @DateTimeFormat(pattern = "hh:mm")
    private Date rentalEnd;
    private Integer rentalTotal;
    @Column(columnDefinition = "VARCHAR(20) DEFAULT '已訂'")//取消 /官方預訂/官方取消
    private String rentalOrderStatus;
    @Column(columnDefinition = "INT DEFAULT 1")
    private Integer paymentStatus; //0 未付/1 已付 /2 官方

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date rentalOrderDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date rentalUpdateDate;

    //////////////////////////////////////

    @PrePersist //在物件轉換到persistent狀態以前，做這個function
    public void onCreate() {
        if(rentalOrderDate==null && rentalUpdateDate==null) {
            rentalOrderDate=new Date();
            rentalUpdateDate=new Date();
            if(rentalOrderStatus==null && paymentStatus==null) {
                rentalOrderStatus="已訂";
                paymentStatus=1;
            }
        }
    }





    @PreUpdate
    public void onUpdate(){
        rentalUpdateDate = new Date();
    }

    /////////////////////////////////////

    public VenueRental() {
    }

    /////////////////////////////////////

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Integer getParticipantsNumber() {
        return participantsNumber;
    }

    public void setParticipantsNumber(Integer participantsNumber) {
        this.participantsNumber = participantsNumber;
    }

    public Integer getDogNumber() {
        return dogNumber;
    }

    public void setDogNumber(Integer dogNumber) {
        this.dogNumber = dogNumber;
    }

    public Date getRentalDate() {
        return rentalDate;
    }

    public void setRentalDate(Date rentalDate) {
        this.rentalDate = rentalDate;
    }

    public Date getRentalStart() {
        return rentalStart;
    }

    public void setRentalStart(Date rentalStart) {
        this.rentalStart = rentalStart;
    }

    public Date getRentalEnd() {
        return rentalEnd;
    }

    public void setRentalEnd(Date rentalEnd) {
        this.rentalEnd = rentalEnd;
    }

    public Integer getRentalTotal() {
        return rentalTotal;
    }

    public void setRentalTotal(Integer rentalTotal) {
        this.rentalTotal = rentalTotal;
    }

    public String getRentalOrderStatus() {
        return rentalOrderStatus;
    }

    public void setRentalOrderStatus(String rentalOrderStatus) {
        this.rentalOrderStatus = rentalOrderStatus;
    }

    public Integer getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(Integer paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getRentalOrderDate() {
        return rentalOrderDate;
    }

    public void setRentalOrderDate(Date rentalOrderDate) {
        this.rentalOrderDate = rentalOrderDate;
    }

    public Date getRentalUpdateDate() {
        return rentalUpdateDate;
    }

    public void setRentalUpdateDate(Date rentalUpdateDate) {
        this.rentalUpdateDate = rentalUpdateDate;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }
}
