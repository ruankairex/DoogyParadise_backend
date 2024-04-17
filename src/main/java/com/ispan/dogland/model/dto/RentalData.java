package com.ispan.dogland.model.dto;

import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class RentalData {
    private Integer rentalId;
    private Integer venueId;
    private Integer userId;
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
    private String rentalOrderStatus;
    private Integer paymentStatus;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date rentalOrderDate;
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date rentalUpdateDate;

    //////////////


    public RentalData() {
    }

    public RentalData(Integer venueId, Integer userId, Integer participantsNumber, Integer dogNumber, Date rentalDate, Date rentalStart, Date rentalEnd, Integer rentalTotal) {
        this.venueId = venueId;
        this.userId = userId;
        this.participantsNumber = participantsNumber;
        this.dogNumber = dogNumber;
        this.rentalDate = rentalDate;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.rentalTotal = rentalTotal;
    }

    public RentalData(Integer rentalId, Integer venueId, Integer userId, Integer participantsNumber, Integer dogNumber, Date rentalDate, Date rentalStart, Date rentalEnd, Integer rentalTotal, String rentalOrderStatus, Integer paymentStatus, Date rentalOrderDate, Date rentalUpdateDate) {
        this.rentalId = rentalId;
        this.venueId = venueId;
        this.userId = userId;
        this.participantsNumber = participantsNumber;
        this.dogNumber = dogNumber;
        this.rentalDate = rentalDate;
        this.rentalStart = rentalStart;
        this.rentalEnd = rentalEnd;
        this.rentalTotal = rentalTotal;
        this.rentalOrderStatus = rentalOrderStatus;
        this.paymentStatus = paymentStatus;
        this.rentalOrderDate = rentalOrderDate;
        this.rentalUpdateDate = rentalUpdateDate;
    }

    public RentalData(Integer userId) {
        this.userId = userId;
    }

    public Integer getRentalId() {
        return rentalId;
    }

    public void setRentalId(Integer rentalId) {
        this.rentalId = rentalId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public Integer getRentalTotal() {
        return rentalTotal;
    }

    public void setRentalTotal(Integer rentalTotal) {
        this.rentalTotal = rentalTotal;
    }
}
