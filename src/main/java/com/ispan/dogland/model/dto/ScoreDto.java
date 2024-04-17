package com.ispan.dogland.model.dto;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.room.Room;

import java.util.Date;

public class ScoreDto {

    private Integer reservationId;

    private Room room;

    private Integer userId;

    private String lastName;

    private Date startTime;

    private Date endTime;

    private Date reservationTime;

    private Integer star;

    private String conments;

    private Date conmentsTime;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
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

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getConments() {
        return conments;
    }

    public void setConments(String conments) {
        this.conments = conments;
    }

    public Date getConmentsTime() {
        return conmentsTime;
    }

    public void setConmentsTime(Date conmentsTime) {
        this.conmentsTime = conmentsTime;
    }
}
