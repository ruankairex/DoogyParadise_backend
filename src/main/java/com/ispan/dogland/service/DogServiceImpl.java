package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class DogServiceImpl implements DogService {


    private DogRepository dogRepository;

    private UserRepository userRepository;

    private Cloudinary cloudinary;

    @Autowired
    public DogServiceImpl(DogRepository dogRepository,UserRepository userRepository, Cloudinary cloudinary) {
        this.userRepository = userRepository;
        this.dogRepository = dogRepository;
        this.cloudinary = cloudinary;
    }
    @Override
    public Users findUserAndDogsByUserId(Integer userId) {
        return userRepository.findUserAndDogsByUserId(userId);
    }

    public List<Dog> findDogsByUsersId(Integer userId) {
        Users user = userRepository.findByUserId(userId);
        return user.getDogs();
    }

    @Override
    public Dog deleteDog(Integer dogId) {
        dogRepository.delete(dogRepository.findByDogId(dogId));
        return dogRepository.findByDogId(dogId);
    }

    @Override
    public Dog addUserDog(Dog dog, Integer userId) {
        dog.setUser(userRepository.findByUserId(userId));
        return dogRepository.save(dog);
    }

    @Override
    public String uploadImg(@RequestParam Integer dogId, @RequestParam MultipartFile dogImgPathCloud) {
        try{
            Map data = this.cloudinary.uploader().upload(dogImgPathCloud.getBytes(), ObjectUtils.asMap("folder", "dogFolder"));
            Dog dog = dogRepository.findByDogId(dogId);
            dog.setDogImgPathCloud((String) data.get("url"));
            dog.setDogImgPublicId((String) data.get("public_id"));
            dogRepository.save(dog);
            return (String) data.get("url");
        }catch (IOException e){
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    @Override
    public void updateDog(Dog dog, Integer dogId) {
        Dog dog1 = dogRepository.findByDogId(dogId);
        dog1.setDogName(dog.getDogName());
        dog1.setDogGender(dog.getDogGender());
        dog1.setDogBirthDate(dog.getDogBirthDate());
        dog1.setDogBreed(dog.getDogBreed());
        dog1.setDogWeight(dog.getDogWeight());
        dog1.setDogIntroduce(dog.getDogIntroduce());
        dogRepository.save(dog1);
    }


}
