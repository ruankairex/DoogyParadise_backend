package com.ispan.dogland.controller;

import com.cloudinary.Cloudinary;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.DogService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dog")
public class DogController {

    @Autowired
    private DogService dogService;


    @GetMapping("/getDogs/{userId}")
    public List<Dog> getDogs(@PathVariable Integer userId) {
        Users user = dogService.findUserAndDogsByUserId(userId);
        List<Dog> dogs = user.getDogs();
        if(dogs.isEmpty()){
            return null;
        }
        return dogs;
    }

    @DeleteMapping("/delete/Dogs/{dogId}")
    public Dog deleteDogs(@PathVariable Integer dogId) {
        return dogService.deleteDog(dogId);
    }

    @PostMapping("/addUserDog")
    public Integer addUserDog(@RequestBody Dog dog, HttpSession session) {
        Passport loginUser = (Passport) session.getAttribute("loginUser");
        Dog dog1 = dogService.addUserDog(dog, loginUser.getUserId());
        return dog1.getDogId();
    }

    @PostMapping("/addDogImg")
    public String addDogImg(Integer dogId, @RequestParam MultipartFile dogImgPathCloud) {
//        System.out.println(dogId);
//        System.out.println(dogImgPathCloud);
        return dogService.uploadImg(dogId, dogImgPathCloud);
    }

    @PostMapping("/update")
    public Integer update(@RequestBody Dog dog, @RequestParam Integer dogId) {
        dogService.updateDog(dog, dogId);
        return dogId;
    }



}
