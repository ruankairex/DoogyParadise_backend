package com.ispan.dogland.model.entity.room;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "room_reservation")
public class RoomReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer reservationId;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="room_id")
    private Room room;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private Users user;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="dog_id")
    private Dog dog;

    private Date startTime;

    private Date endTime;

    private Integer totalPrice;

    private Date reservationTime;

    private Date cancelTime;

    private String cancelDirection;

    private String paymentMethod;

    private String paymentStatus;

    private Integer star;

    private String conments;

    private Date conmentsTime;

    private String conmentsClass;

    public RoomReservation() {
    }

    public RoomReservation(Integer reservationId, Room room, Users user, Dog dog, Date startTime, Date endTime, Integer totalPrice, Date reservationTime, Date cancelTime, String cancelDirection, String paymentMethod, String paymentStatus, Integer star, String conments, Date conmentsTime, String conmentsClass) {
        this.reservationId = reservationId;
        this.room = room;
        this.user = user;
        this.dog = dog;
        this.startTime = startTime;
        this.endTime = endTime;
        this.totalPrice = totalPrice;
        this.reservationTime = reservationTime;
        this.cancelTime = cancelTime;
        this.cancelDirection = cancelDirection;
        this.paymentMethod = paymentMethod;
        this.paymentStatus = paymentStatus;
        this.star = star;
        this.conments = conments;
        this.conmentsTime = conmentsTime;
        this.conmentsClass = conmentsClass;
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

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
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

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
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

    public String getConmentsClass() {
        return conmentsClass;
    }

    public void setConmentsClass(String conmentsClass) {
        this.conmentsClass = conmentsClass;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("RoomReservation{");
        sb.append("reservationId=").append(reservationId);
        sb.append(", room=").append(room);
        sb.append(", user=").append(user);
        sb.append(", dog=").append(dog);
        sb.append(", startTime=").append(startTime);
        sb.append(", endTime=").append(endTime);
        sb.append(", totalPrice=").append(totalPrice);
        sb.append(", reservationTime=").append(reservationTime);
        sb.append(", cancelTime=").append(cancelTime);
        sb.append(", cancelDirection='").append(cancelDirection).append('\'');
        sb.append(", paymentMethod='").append(paymentMethod).append('\'');
        sb.append(", paymentStatus='").append(paymentStatus).append('\'');
        sb.append(", star=").append(star);
        sb.append(", conments='").append(conments).append('\'');
        sb.append(", conmentsTime=").append(conmentsTime);
        sb.append(", conmentsClass='").append(conmentsClass).append('\'');
        sb.append('}');
        return sb.toString();
    }
}