package com.ispan.dogland.model.entity.room;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer roomId;

    private Integer roomName;

    private Integer roomSize;

    private Integer roomPrice;

    private String roomIntroduction;

    private String roomImgPath;

    @OneToMany(mappedBy = "room",
            fetch = FetchType.LAZY ,
            cascade = {CascadeType.ALL})
    @JsonIgnore
    private List<RoomReservation> roomReservation;

    public List<RoomReservation> getRoomReservation() {
        return roomReservation;
    }

    public void setRoomReservation(List<RoomReservation> roomReservation) {
        this.roomReservation = roomReservation;
    }

    public void add(RoomReservation tmpRoomReservation){
        if(roomReservation==null){
            roomReservation = new ArrayList<>();
        }
        roomReservation.add(tmpRoomReservation);

        tmpRoomReservation.setRoom(this);
    }

    public Room() {
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public Integer getRoomName() {
        return roomName;
    }

    public void setRoomName(Integer roomName) {
        this.roomName = roomName;
    }

    public Integer getRoomSize() {
        return roomSize;
    }

    public void setRoomSize(Integer roomSize) {
        this.roomSize = roomSize;
    }

    public Integer getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(Integer roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getRoomIntroduction() {
        return roomIntroduction;
    }

    public void setRoomIntroduction(String roomIntroduction) {
        this.roomIntroduction = roomIntroduction;
    }

    public String getRoomImgPath() {
        return roomImgPath;
    }

    public void setRoomImgPath(String roomImgPath) {
        this.roomImgPath = roomImgPath;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Room{");
        sb.append("roomId=").append(roomId);
        sb.append(", roomName=").append(roomName);
        sb.append(", roomSize=").append(roomSize);
        sb.append(", roomPrice=").append(roomPrice);
        sb.append(", roomIntroduction='").append(roomIntroduction).append('\'');
        sb.append(", roomImgPath='").append(roomImgPath).append('\'');
        sb.append('}');
        return sb.toString();
    }

}