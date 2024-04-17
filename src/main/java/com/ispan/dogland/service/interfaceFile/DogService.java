package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DogService {
    public Users findUserAndDogsByUserId(Integer userId);

    public List<Dog> findDogsByUsersId(Integer userId);

    public Dog deleteDog(Integer dogId);

    public Dog addUserDog(Dog dog, Integer userId);

    public String uploadImg(Integer dogId, MultipartFile dogImgPathCloud);

    public void updateDog(Dog dog, Integer dogId);
}
