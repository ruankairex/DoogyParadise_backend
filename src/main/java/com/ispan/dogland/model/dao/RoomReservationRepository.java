package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomReservationRepository extends JpaRepository<RoomReservation,Integer> {

    @Query("SELECT r FROM RoomReservation r ORDER BY r.startTime DESC")
    List<RoomReservation> findAll();

    @Query("SELECT r FROM RoomReservation r WHERE r.user = ?1 ORDER BY r.startTime DESC")
    List<RoomReservation> findByUser(Users user);

    RoomReservation findByReservationId(Integer reservationId);

}