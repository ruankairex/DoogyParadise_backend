package com.ispan.dogland.model.dto;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.room.Room;

import java.util.Date;

public class RoomReservationDto {

    private Integer reservationId;

    private Room room;

    private Integer userId;

    private String lastName;

    private Dog dog;

    private Date startTime;

    private Date endTime;

    private Integer totalPrice;

    private Date reservationTime;

    private Date cancelTime;

    private String cancelDirection;

    private Integer star;

    private String conments;

    private Date conmentsTime;

    public RoomReservationDto() {
    }

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

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
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

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Date reservationTime) {
        this.reservationTime = reservationTime;
    }

    public Date getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(Date cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getCancelDirection() {
        return cancelDirection;
    }

    public void setCancelDirection(String cancelDirection) {
        this.cancelDirection = cancelDirection;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomReservation{");
        sb.append("reservationId=").append(reservationId);
        sb.append(", room=").append(room);
        sb.append(", userId=").append(userId);
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", dog=").append(dog);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", reservationTime=").append(reservationTime);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", cancelDirection='").append(cancelDirection).append('\'');
        sb.append(", star=").append(star);
        sb.append(", conments='").append(conments).append('\'');
        sb.append(", conmentsTime=").append(conmentsTime);
        sb.append('}');
        return sb.toString();
    }
}
