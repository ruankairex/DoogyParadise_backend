package com.ispan.dogland.controller;

import com.ispan.dogland.model.dto.Passport;
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
public class RoomControllerOpen {

    @Autowired
    private RoomService rService;

    @Autowired
    private DogService dogService;

    // 顯示出全部的房間
    @GetMapping("")
    public List<Room> room() {
        return rService.findAllroom();
    }
    

}
