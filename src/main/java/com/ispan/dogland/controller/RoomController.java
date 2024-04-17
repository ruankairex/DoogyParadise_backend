package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.RoomOrderDto;
import com.ispan.dogland.model.dto.RoomReservationDto;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.service.interfaceFile.DogService;
import com.ispan.dogland.service.interfaceFile.RoomService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private RoomService rService;

    @Autowired
    private DogService dogService;

    // 新增訂房明細
    @PostMapping("/roomReservation")
    public Integer addRoom(@RequestBody RoomReservation roomReservation,
                        @RequestParam Integer roomId,
                        @RequestParam Integer dogId,
                        HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        return rService.addRoomReservation(roomReservation, roomId, dogId, loginUser.getUserId());
    }

    // 寄 email
    @PostMapping("/email")
    public void email(HttpSession session, @RequestParam Integer roomReservationId) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        rService.sendEmail(loginUser.getUserId(), roomReservationId);
    }

    // 修改訂單時段
    @PostMapping("/updateRoom")
    public void updateRoom(@RequestBody RoomReservation roomReservation,
                           @RequestParam Integer roomReservationId) {
        rService.updateRoomReservation(roomReservation, roomReservationId);
    }

    // 取消訂單
    @PostMapping("/cancel")
    public void cancel(@RequestBody RoomReservation roomReservation,
                       @RequestParam Integer roomReservationId) {
        rService.cancelRoomReservation(roomReservation, roomReservationId);
    }

    // 評分
    @PostMapping("/score")
    public void score(@RequestBody RoomReservation roomReservation,
                      @RequestParam Integer roomReservationId) {
        rService.scoreRoomReservation(roomReservation, roomReservationId);
    }

    // 顯示出使用者的全部訂房明細
    @GetMapping("/allRoomReservationByUser")
    public List<RoomReservationDto> roomReservation(HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        return rService.findRoomReservationByUserId(loginUser.getUserId());
    }

    // 顯示出使用者的未結束訂房明細
    @GetMapping("/reservation/order")
    public List<RoomOrderDto> order(HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        return rService.findOrderByUserId(loginUser.getUserId());
    }

    // 顯示出使用者的狗
    @GetMapping("/dog")
    public List<Dog> findDogsByUsersId(HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        return dogService.findDogsByUsersId(loginUser.getUserId());
    }

    // 顯示全部的訂單時間 就算不是這個使用者的也需要
    @GetMapping("/reservation")
    public List<List<String>> reservationTime() {
        return rService.roomReservation();
    }

    // 修改訂單時段(顯示需要修改的訂單明細)
    @GetMapping("/showUpdateRoom")
    public RoomReservationDto updateRoom(@RequestParam Integer roomReservationId) {
        return rService.findDtoByRoomReservationId(roomReservationId);
    }

}
