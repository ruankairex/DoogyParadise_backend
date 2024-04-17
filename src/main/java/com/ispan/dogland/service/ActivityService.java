package com.ispan.dogland.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.activity.*;
import com.ispan.dogland.model.dto.*;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.*;

import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ActivityService {
    @Value("${gemini_apiKey}")
    private String apiKey;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    freemarker.template.Configuration freemarkerConfig;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DogRepository dogRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private VenueRepository venueRepository;
    @Autowired
    private VenueRentalRepository rentalRepository;
    @Autowired
    private VenueActivityRepository activityRepository;
    @Autowired
    private LikedActivityRepository likedRepository;
    @Autowired
    private CommentActivityRepository commentRepository;
    @Autowired
    private ActivityTypeRepository typeRepository;
    @Autowired
    private ActivityGalleryRepository galleryRepository;
    @Autowired
    private ActivityDogJoinedRepository dogJoinedRepository;
    @Autowired
    private  ActivityUserJoinedRepository userJoinedRepository;

    //////////////////////場地////////////////////////

    //===============尋找venue==================
    public Venue getVenueByName(String venueName){
        return venueRepository.findByVenueName(venueName);
    }
    public Venue getVenueById(Integer venueId){
        return venueRepository.findByVenueId(venueId);
    }

    //===============列出所有venue==================
    public List<Venue> getAllVenue(){
        return venueRepository.findAll();
    }

    ///////////////////////活動類型/////////////////////////

    //===============新增活動類型===================
    public ActivityType createType(String activityType){
        ActivityType type = new ActivityType();
        type.setActivityTypeName(activityType);
        return typeRepository.save(type);
    }

    //===============查詢活動類型===================
    public  ActivityType searchType(String activityType){
        return typeRepository.findByActivityTypeName(activityType);
    }

    //===============列出所有活動類型===================
    public List<ActivityType> allActivityType(){
        return typeRepository.findAll();
    }


    ///////////////////////場地租借/////////////////////////
    //===============新增場地租借訂單===================
    public RentalData addNewRental(RentalData rentalData){
        Integer venueId = rentalData.getVenueId();
        Integer userId = rentalData.getUserId();
        Users user = userRepository.findByUserId(userId);
        Venue venue = venueRepository.findByVenueId(venueId);
        VenueRental rental = new VenueRental();
        BeanUtils.copyProperties(rentalData,rental);
        rental.setUser(user);
        rental.setVenue(venue);
        VenueRental save = rentalRepository.save(rental);

        RentalData newData = new RentalData();
        BeanUtils.copyProperties(save,newData);
        newData.setUserId(save.getUser().getUserId());
        newData.setVenueId(save.getVenue().getVenueId());
        return newData;
    }
    public RentalData addOfficialRental(RentalData rentalData){
        Integer venueId = rentalData.getVenueId();


        Venue venue = venueRepository.findByVenueId(venueId);
        VenueRental rental = new VenueRental();
        BeanUtils.copyProperties(rentalData,rental);
        rental.setUser(null);
        rental.setVenue(venue);
        VenueRental save = rentalRepository.save(rental);

        RentalData newData = new RentalData();
        BeanUtils.copyProperties(save,newData);
        newData.setUserId(null);
        newData.setVenueId(save.getVenue().getVenueId());
        return newData;
    }

    //===============查詢全部場地租借訂單===================

    public Page<RentalData> findRentalByPage(Integer pageNumber){
        Page<VenueRental> rental = rentalRepository.findAll(PageRequest.of(pageNumber, 9));
        System.out.println(rental.getTotalElements());
        Page<RentalData> rentalDataList = rental.map(r -> {
                RentalData rentalData = new RentalData();
                BeanUtils.copyProperties(r, rentalData);
                rentalData.setVenueId(r.getVenue().getVenueId());
                if (r.getUser() != null) {
                    rentalData.setUserId(r.getUser().getUserId());
                }else{
                    rentalData.setUserId(0);
                }
                return rentalData;
            });
            return rentalDataList;
    }

    //===============查詢某使用者場地租借訂單===================
    public Page<RentalData> findUserRetalByPage(Integer userId,Integer pageNumber){
        Users user=userRepository.findByUserId(userId);
        Page<VenueRental> rental = rentalRepository.findByUser(user,PageRequest.of(pageNumber, 9));
        System.out.println(rental.getTotalElements());
        Page<RentalData> rentalDataList = rental.map(r -> {
            RentalData rentalData = new RentalData();
            BeanUtils.copyProperties(r, rentalData);
            rentalData.setVenueId(r.getVenue().getVenueId());
            if (r.getUser() != null) {
                rentalData.setUserId(r.getUser().getUserId());
            }
            return rentalData;
        });
        return rentalDataList;
    }
    //===============查詢官方場地租借訂單===================
    public Page<RentalData> findOfficialRentalByPage(Integer pageNumber){
        Page<VenueRental> officialRental = rentalRepository.findByUserNull(PageRequest.of(pageNumber, 9));
        System.out.println(officialRental.getTotalElements());
        Page<RentalData> rentalDataList=officialRental.map(r->{
            RentalData rentalData = new RentalData();
            BeanUtils.copyProperties(r,rentalData);
            rentalData.setVenueId((r.getVenue().getVenueId()));
            rentalData.setUserId(0);
            return rentalData;
        });
        return rentalDataList;
    }


    ///////////////////////場地活動/////////////////////////
    //===============新增場地活動===================
    public ActivityData addNewActivity(ActivityData activityData){
        Integer typeId = activityData.getActivityTypeId();
        ActivityType type = typeRepository.findByActivityTypeId(typeId);
        Integer employeeId = activityData.getEmployeeId();
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Integer venueId = activityData.getVenueId();
        Venue venue = venueRepository.findByVenueId(venueId);
        VenueActivity activity = new VenueActivity();
        BeanUtils.copyProperties(activityData,activity);
        activity.setEmployee(employee);
        activity.setVenue(venue);
        activity.setActivityType(type);
        VenueActivity save = activityRepository.save(activity);

        ActivityData newData = new ActivityData();
        BeanUtils.copyProperties(save,newData);
        newData.setActivityId(save.getActivityId());
        newData.setEmployeeId(save.getEmployee().getEmployeeId());
        newData.setActivityTypeId(save.getActivityType().getActivityTypeId());
        newData.setVenueId(save.getVenue().getVenueId());
        return  newData;
    }
    public ActivityCreateDto offCreateNewActivity(ActivityCreateDto createDto){
        VenueActivity activity = new VenueActivity();
        BeanUtils.copyProperties(createDto,activity);
        activity.setActivityType(typeRepository.findByActivityTypeId(createDto.getActivityTypeId()));
        activity.setVenue(venueRepository.findByVenueId(createDto.getVenueId()));
        activity.setEmployee(employeeRepository.findByEmployeeId(createDto.getEmployeeId()));
        VenueActivity save = activityRepository.save(activity);
        ActivityCreateDto dto = new ActivityCreateDto();
        BeanUtils.copyProperties(save,dto);
        dto.setActivityTypeId(createDto.getActivityTypeId());
        dto.setVenueId(createDto.getVenueId());
        dto.setEmployeeId(createDto.getEmployeeId());
        return dto;
    }

    //===============新增活動照片===================
    public ActivityGallery addTitleImg(MultipartFile file,Integer activityId){
        //上傳到producutFolder裡
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
            VenueActivity activity = activityRepository.findByActivityId(activityId);

            ActivityGallery gallery = new ActivityGallery();
            gallery.setVenueActivity(activity);
            gallery.setGalleryImgUrl((String) data.get("url"));
            gallery.setGalleryPublicId((String) data.get("public_id"));
            gallery.setGalleryImgType("main");

            System.out.println("活動id: "+activityId+" 的主題照片上傳成功!");

            activity.setActivityUpdateDate(new Date());
            activityRepository.save(activity);

            return galleryRepository.save(gallery);
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    public ActivityGallery addNormalImg(MultipartFile file,Integer activityId){
        //上傳到producutFolder裡
        try {
            Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
            VenueActivity activity = activityRepository.findByActivityId(activityId);

            ActivityGallery gallery = new ActivityGallery();
            gallery.setVenueActivity(activity);
            gallery.setGalleryImgUrl((String) data.get("url"));
            gallery.setGalleryPublicId((String) data.get("public_id"));
            gallery.setGalleryImgType(null);

            System.out.println("活動id: "+activityId+" 的其他說明照片上傳成功!");

            activity.setActivityUpdateDate(new Date());
            activityRepository.save(activity);

            return galleryRepository.save(gallery);
        } catch (IOException e) {
            throw new RuntimeException("Image uploading fail !!");
        }
    }

    //===============所有活動簡述===============
    public Page<ActivityBrief> findActivityByPage(Integer pageNumber){
        Page<VenueActivity> all = activityRepository.findAllByOrderByActivityDateDesc(PageRequest.of(pageNumber-1, 6));

        Page<ActivityBrief> briefs = all.map(a->{
            ActivityBrief ab = new ActivityBrief();
            BeanUtils.copyProperties(a,ab);

            Integer activityId = a.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
            ab.setGalleryImgUrl(main.getGalleryImgUrl());

            List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
            Integer likedTime = likeList.size();
            ab.setLikedTime(likedTime);

            ab.setActivityTypeName(a.getActivityType().getActivityTypeName());
            ab.setVenueName(a.getVenue().getVenueName());
            return ab;
        });
        return briefs;
    }

    //===============依類別找活動簡述===============
    public Page<ActivityBrief> findActivityByType(Integer typeId, Integer pageNumber){
        ActivityType type = typeRepository.findByActivityTypeId(typeId);
        Page<VenueActivity> activities = activityRepository.findByActivityType(type,PageRequest.of(pageNumber-1, 4));
        System.out.println(activities.getTotalElements());
        Page<ActivityBrief> activityDataList = activities.map(r -> {
            ActivityBrief brief = new ActivityBrief();
            BeanUtils.copyProperties(r, brief);
            brief.setActivityTypeName(r.getActivityType().getActivityTypeName());
            brief.setVenueName(r.getVenue().getVenueName());

            Integer activityId = r.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");

            List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
            Integer likedTime = likeList.size();
            brief.setLikedTime(likedTime);
            brief.setGalleryImgUrl(main.getGalleryImgUrl());
            return brief;
        });
        return activityDataList;
    }

    //===============依使用者找他的狗===============
    public List<Dog> findUsersDog(Integer userId){
        Users user = userRepository.findByUserId(userId);
        return dogRepository.findByUser(user);
    }

    //===============使用者與狗狗報名活動===============
    public VenueActivity findActivityByActivityId(Integer activityId){
        return activityRepository.findByActivityId(activityId);
    }
    public ActivityUserJoined userApply(Integer userId, String note,Integer activityId){
        Users user = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        ActivityUserJoined joined = userJoinedRepository.findByVenueActivityAndUserAndJoinedStatusNot(activity, user,"已取消");

        if(joined==null) { //要再看有沒有取消過
            ActivityUserJoined joinedPast = userJoinedRepository.findByVenueActivityAndUserAndJoinedStatusNot(activity, user, "參加");
            if(joinedPast!=null){
                joinedPast.setUserNote(note);
                joinedPast.setJoinedTime(new Date());
                joinedPast.setJoinedStatus("參加");

                activity.setCurrentUserNumber(activity.getCurrentUserNumber()+1);
                activityRepository.save(activity);
                return userJoinedRepository.save(joinedPast);

            }else{
                ActivityUserJoined userJoined = new ActivityUserJoined();
                userJoined.setUserNote(note);
                userJoined.setUser(user);
                userJoined.setVenueActivity(activity);

                activity.setCurrentUserNumber(activity.getCurrentUserNumber() + 1);
                activityRepository.save(activity);
                return userJoinedRepository.save(userJoined);
            }

        }else{
            joined.setUpdateTime(new Date());
            return joined;
        }
    }

    public ActivityDogJoined dogApply(Integer dogId,Integer activityId){
        Dog dog = dogRepository.findByDogId(dogId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        ActivityDogJoined joined = dogJoinedRepository.findByVenueActivityAndDog(activity, dog);
        if(joined==null){
            if(activity.getActivityDogNumber()>activity.getCurrentDogNumber()){
                ActivityDogJoined dogJoined = new ActivityDogJoined();
                dogJoined.setDog(dog);
                dogJoined.setVenueActivity(activity);

                activity.setCurrentDogNumber(activity.getCurrentDogNumber()+1);
                //判斷加完有沒有額滿
                if(activity.getCurrentDogNumber()==activity.getActivityDogNumber()){
                    activity.setActivityStatus("已額滿");
                }
                activityRepository.save(activity);
                //修改使用者單更新時間
                Users user = dogJoined.getDog().getUser();
                ActivityUserJoined userJoined = userJoinedRepository.findByVenueActivityAndUser(activity, user);
                userJoined.setUpdateTime(new Date());
                userJoinedRepository.save(userJoined);

                return dogJoinedRepository.save(dogJoined);
            }else{
                throw new RuntimeException("超過狗數限制 !!");
            }
        }else{//繼續篩有沒有取消過
            ActivityDogJoined dogNotAttend = dogJoinedRepository.findByVenueActivityAndDogAndJoinedStatusNot(activity, dog, "參加");
            if(dogNotAttend!=null){//曾經取消的狗
                if(activity.getActivityDogNumber()>activity.getCurrentDogNumber()){
                    dogNotAttend.setJoinedStatus("參加");
                    activity.setCurrentDogNumber(activity.getCurrentDogNumber()+1);
                    //判斷加完有沒有額滿
                    if(activity.getCurrentDogNumber()==activity.getActivityDogNumber()){
                        activity.setActivityStatus("已額滿");
                    }
                    activityRepository.save(activity);
                    //修改使用者單更新時間
                    Users user = dogNotAttend.getDog().getUser();
                    ActivityUserJoined userJoined = userJoinedRepository.findByVenueActivityAndUser(activity, user);
                    userJoined.setUpdateTime(new Date());
                    userJoinedRepository.save(userJoined);
                    return dogJoinedRepository.save(dogNotAttend);
                }else{
                    throw new RuntimeException("超過狗數限制 !!");
                }
            }else{//沒參加過的狗
                throw new RuntimeException("已經報名了");
            }
        }
    }

    //找出使用者有參加且沒取消的狗
    public List<Dog> findUserDogsAttendThisActivity(Integer userId,Integer activityId){
        Users user = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        //先看這個使用者有沒有參加且沒取消
        ActivityUserJoined joined = userJoinedRepository.findByVenueActivityAndUserAndJoinedStatusNot(activity, user,"已取消");
        if (joined!=null){
            //取出這個使用者所有狗
            List<Dog> dogList = dogRepository.findByUser(user);
            List<Dog> joinedDogList=new ArrayList<>();//這個裝檢查後的資料
            for(Dog dog:dogList){
                //狗有參加且沒取消
                ActivityDogJoined checkDog = dogJoinedRepository.findByVenueActivityAndDogAndJoinedStatusNot(activity, dog,"已取消");
                if(checkDog!=null){
                    joinedDogList.add(dog);
                }
            }
            return joinedDogList;
        }else{
            System.out.println("根本沒參加過或已取消");
            return null;
        }
    }
    //找出沒有報名且沒取消的狗
    //check使用者有沒有報名過
    //if false-> 給全部狗
    //if true->檢查狗有沒有報名過
    //          ->true:檢查有沒有取消
//                        ->true:沒有取消:null
//                        ->false:又取消:列入
    //          ->false:列入
    public List<Dog> findUserDogsStayAtHome(Integer userId,Integer activityId){
        Users user = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        //先看這個使用者有沒有參加
        ActivityUserJoined joined = userJoinedRepository.findByVenueActivityAndUser(activity, user);
        //取出這個使用者所有狗
        List<Dog> dogList = dogRepository.findByUser(user);
        if (joined!=null){
            List<Dog> joinedDogList=new ArrayList<>();//這個裝檢查後的資料
            for(Dog dog:dogList){
                ActivityDogJoined checkDog = dogJoinedRepository.findByVenueActivityAndDog(activity, dog);
                if(checkDog==null){//這隻狗沒參加過
                    joinedDogList.add(dog);
                }else{//這隻狗有參加過
                    ActivityDogJoined dogCancelled = dogJoinedRepository.findByVenueActivityAndDogAndJoinedStatusNot(activity, dog, "參加");
                    if(dogCancelled!=null){//如過他狀態不是參加
                        joinedDogList.add(dog);
                    }
                }
            }
            return joinedDogList;
        }else{
           return dogList;
        }
    }

    public List<MyActivitiesDto>findUserAllJoinedActivities(Integer userId){
        Users user = userRepository.findByUserId(userId);
        //取得所有使用者有參加的活動
        List<ActivityUserJoined> userJoinedList = userJoinedRepository.findByUserAndJoinedStatusNot(user, "已取消");
        List<MyActivitiesDto> myActivities=new ArrayList<>();
        //在這些有參加的活動中找有參加的狗
        for(ActivityUserJoined userJoined:userJoinedList){
            MyActivitiesDto dto = new MyActivitiesDto();
            String userNote = userJoined.getUserNote();
            VenueActivity activity = userJoined.getVenueActivity();
            List<Dog> dogList = findUserDogsAttendThisActivity(userId, activity.getActivityId());
            List<String> dogJoinedList=new ArrayList<>();
            for(Dog dog:dogList){
                String dogName = dog.getDogName();
                dogJoinedList.add(dogName);
            }
            BeanUtils.copyProperties(activity,dto);//複製活動信息
            BeanUtils.copyProperties(userJoined,dto);//複製使用者報名時的信息
            dto.setDogList(dogJoinedList);
            myActivities.add(dto);
        }
        return myActivities;
    }

    //取消報名
    //找到使用者->該使用者所有有報名的狗->取消數是否==原先有報名的狗數
    // true:全部取消 ->使用者報名狀態更新 && 狗狗狀態更新->找到這個活動更新狗數 更新人數
    // false:部分取消 ->使用者更新時間 && 狗狗狀態更新->找到這個活動更新狗數

    public MyActivitiesDto userCancelledActivity(Integer userId, Integer[] dogIdList,Integer activityId){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        Users user = userRepository.findByUserId(userId);
        ActivityUserJoined userJoined = userJoinedRepository.findByVenueActivityAndUser(activity, user);
        List<Dog> oldDogList = findUserDogsAttendThisActivity(userId, activityId);
        List<String> doCancelledDogList = new ArrayList<>();
        if(oldDogList.size()<=dogIdList.length){
            userJoined.setJoinedStatus("已取消");
            for(Integer dogId:dogIdList){
                Dog dog = dogRepository.findByDogId(dogId);
                ActivityDogJoined dogJoined = dogJoinedRepository.findByVenueActivityAndDog(activity, dog);
                dogJoined.setJoinedStatus("已取消");
                ActivityDogJoined dogChange = dogJoinedRepository.save(dogJoined);
                //紀錄已取消的狗名
                String dogName = dog.getDogName();
                doCancelledDogList.add(dogName);
            }
            ActivityUserJoined userChange = userJoinedRepository.save(userJoined);
            activity.setCurrentUserNumber(activity.getCurrentUserNumber()-1);//更新人數
            activity.setCurrentDogNumber(activity.getCurrentDogNumber()-dogIdList.length);//更新狗數
            VenueActivity activityChange = activityRepository.save(activity);
            //記錄到dto中
            MyActivitiesDto dto = new MyActivitiesDto();
            BeanUtils.copyProperties(activityChange,dto);
            BeanUtils.copyProperties(userChange,dto);
            dto.setDogList(doCancelledDogList);
            return dto;
        }else{
            userJoined.setUpdateTime(new Date());
            for(Integer dogId:dogIdList){
                Dog dog = dogRepository.findByDogId(dogId);
                ActivityDogJoined dogJoined = dogJoinedRepository.findByVenueActivityAndDog(activity, dog);
                dogJoined.setJoinedStatus("已取消");
                dogJoinedRepository.save(dogJoined);
                //紀錄已取消的狗名
                String dogName = dog.getDogName();
                doCancelledDogList.add(dogName);
            }
            ActivityUserJoined userChange = userJoinedRepository.save(userJoined);
            activity.setCurrentDogNumber(activity.getCurrentDogNumber()-dogIdList.length);//更新狗數
            VenueActivity activityChange = activityRepository.save(activity);
            //記錄到dto中
            MyActivitiesDto dto = new MyActivitiesDto();
            BeanUtils.copyProperties(activityChange,dto);
            BeanUtils.copyProperties(userChange,dto);
            dto.setDogList(doCancelledDogList);
            return dto;
        }
    }

    //===============使用者活動管理頁面更改備註===============
    public ActivityUserJoined renewUserNote(Integer activityId,Integer userId,String userNote){
        Users user = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        ActivityUserJoined joined = userJoinedRepository.findByVenueActivityAndUser(activity, user);
        joined.setUserNote(userNote);
        return userJoinedRepository.save(joined);
    }

    //===============請求員工資料===============
    public EmployeeDto getEmployeePass(Integer employeeId){
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        EmployeeDto dto = new EmployeeDto();
        BeanUtils.copyProperties(employee,dto);
        return dto;
    }
    //===============所有過去活動簡述===============
    public Page<ActivityBrief> findPastActivityByPage(Integer pageNumber){
        Page<VenueActivity> all = activityRepository.findByActivityStatusOrderByActivityDateAsc("活動已結束",PageRequest.of(pageNumber-1, 6));

        Page<ActivityBrief> briefs = all.map(a->{
            ActivityBrief ab = new ActivityBrief();
            BeanUtils.copyProperties(a,ab);

            Integer activityId = a.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
            ab.setGalleryImgUrl(main.getGalleryImgUrl());
            List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
            Integer likedTime = likeList.size();
            ab.setLikedTime(likedTime);

            ab.setActivityTypeName(a.getActivityType().getActivityTypeName());
            ab.setVenueName(a.getVenue().getVenueName());
            return ab;
        });
        return briefs;
    }

    //===============過去類別活動簡述===============
    public Page<ActivityBrief> findPastActByCategory(Integer pageNumber,Integer typeId){
        ActivityType type = typeRepository.findByActivityTypeId(typeId);
        Page<VenueActivity> all = activityRepository.findByActivityTypeAndActivityStatusOrderByActivityDateAsc(type,"活動已結束",PageRequest.of(pageNumber-1, 6));

        Page<ActivityBrief> briefs = all.map(a->{
            ActivityBrief ab = new ActivityBrief();
            BeanUtils.copyProperties(a,ab);

            Integer activityId = a.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
            ab.setGalleryImgUrl(main.getGalleryImgUrl());
            List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
            Integer likedTime = likeList.size();
            ab.setLikedTime(likedTime);

            ab.setActivityTypeName(a.getActivityType().getActivityTypeName());
            ab.setVenueName(a.getVenue().getVenueName());
            return ab;
        });
        return briefs;
    }
    //===============所有現在活動簡述===============
    public Page<ActivityBrief> findNowActivityByPage(Integer pageNumber){
        Page<VenueActivity> all = activityRepository.findByActivityStatusNotOrderByActivityDateAsc("活動已結束",PageRequest.of(pageNumber-1, 6));

        Page<ActivityBrief> briefs = all.map(a->{
            ActivityBrief ab = new ActivityBrief();
            BeanUtils.copyProperties(a,ab);

            Integer activityId = a.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
            ab.setGalleryImgUrl(main.getGalleryImgUrl());
            List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
            Integer likedTime = likeList.size();
            ab.setLikedTime(likedTime);

            ab.setActivityTypeName(a.getActivityType().getActivityTypeName());
            ab.setVenueName(a.getVenue().getVenueName());
            return ab;
        });
        return briefs;
    }

    //===============現在類別活動簡述===============
    public Page<ActivityBrief> findNowActByCategory(Integer pageNumber,Integer typeId){
        ActivityType type = typeRepository.findByActivityTypeId(typeId);
        Page<VenueActivity> all = activityRepository.findByActivityTypeAndActivityStatusNotOrderByActivityDateAsc(type,"活動已結束",PageRequest.of(pageNumber-1, 6));

        Page<ActivityBrief> briefs = all.map(a->{
            ActivityBrief ab = new ActivityBrief();
            BeanUtils.copyProperties(a,ab);

            Integer activityId = a.getActivityId();
            VenueActivity activity = activityRepository.findByActivityId(activityId);
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
            ab.setGalleryImgUrl(main.getGalleryImgUrl());
            List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
            Integer likedTime = likeList.size();
            ab.setLikedTime(likedTime);

            ab.setActivityTypeName(a.getActivityType().getActivityTypeName());
            ab.setVenueName(a.getVenue().getVenueName());
            return ab;
        });
        return briefs;
    }

    //===============以區間找現在活動===============
    public List<ActivityBrief> officialActManagerByStatusNot(Date start,Date end){
        List<VenueActivity> allEnd = activityRepository.findByActivityStatusNotAndActivityDateBetweenOrderByActivityDateAsc("活動已結束", start, end);
        List<ActivityBrief> abList = new ArrayList<>();//裝資料的
        for(VenueActivity one:allEnd){
            ActivityBrief brief = new ActivityBrief();

            BeanUtils.copyProperties(one,brief);//venueActivity
            List<LikedActivity> likeList = likedRepository.findByVenueActivity(one);
            Integer likedTime = likeList.size();
            brief.setLikedTime(likedTime);
            brief.setVenueName(one.getVenue().getVenueName());
            brief.setActivityTypeName(one.getActivityType().getActivityTypeName());
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(one, "main");
            brief.setGalleryImgUrl(main.getGalleryImgUrl());
            abList.add(brief);
        }
        return abList;
    }

    //===============以區間找過去活動===============
    public List<ActivityPastBrief> officialActManagerByStatus(Date start,Date end){
        List<VenueActivity> allEnd = activityRepository.findByActivityStatusAndActivityDateBetweenOrderByActivityDateAsc("活動已結束", start, end);
        List<ActivityPastBrief> abList = new ArrayList<>();//裝資料的
        for(VenueActivity one:allEnd){
            ActivityPastBrief brief = new ActivityPastBrief();

            BeanUtils.copyProperties(one,brief);//venueActivity
            List<LikedActivity> likeList = likedRepository.findByVenueActivity(one);
            Integer likedTime = likeList.size();
            brief.setLikedTime(likedTime);
            List<CommentActivity> commentList=commentRepository.findByVenueActivity(one);
            Integer commentTime = commentList.size();
            brief.setCommentedTime(commentTime);
            brief.setVenueName(one.getVenue().getVenueName());
            brief.setActivityTypeName(one.getActivityType().getActivityTypeName());
            ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(one, "main");
            brief.setGalleryImgUrl(main.getGalleryImgUrl());
            abList.add(brief);
        }
        return abList;
    }

    //===============找某個活動 過去/現在 活動所有參加者跟狗資料===============
    public List<ActOfficialAttendeeDto> findOneActAttendeeList(Integer activityId,String joinStatus){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        List<ActivityUserJoined> userJoinedList = userJoinedRepository.findByVenueActivityAndJoinedStatusNot(activity, joinStatus);
        List<ActOfficialAttendeeDto> dtoList=new ArrayList<>();
        for(ActivityUserJoined userJoined:userJoinedList){
            ActOfficialAttendeeDto dto=new ActOfficialAttendeeDto();
            //userJoined 資料
            BeanUtils.copyProperties(userJoined,dto);
            //activity 資料
            dto.setActivityId(activityId);
            //user 資料
            Users user = userJoined.getUser();
            dto.setUserId(user.getUserId());
            dto.setFirstName(user.getFirstName());
            //dog 資料
            List<Dog> dogList = findUserDogsAttendThisActivity(user.getUserId(), activityId);
            List<String> dogNameList =new ArrayList<>();
            List<String> dogProfileList =new ArrayList<>();
            for(Dog dog:dogList){
                String img = dog.getDogImgPathCloud();
                String dogName = dog.getDogName();
                dogNameList.add(dogName);
                dogProfileList.add(img);
            }
            dto.setDogNameList(dogNameList);
            dto.setDogProfileList(dogProfileList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    //===============activityShowPage===============
    public ActivityShowInfo getActivityPageInfo(Integer activityId){
        //activity
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        String activityTypeName = activity.getActivityType().getActivityTypeName();
        Integer venueId = activity.getVenue().getVenueId();
        String venueName = activity.getVenue().getVenueName();
        //like
        List<LikedActivity> liked = likedRepository.findByVenueActivity(activity);
        Integer likedTime = liked.size();
        //gallery
        List<ActivityGallery> galleryList = galleryRepository.findByVenueActivity(activity);
        List<ActivityGalleryDto> imgList = new ArrayList<>();
        for(ActivityGallery img:galleryList){
            ActivityGalleryDto dto = new ActivityGalleryDto();
            BeanUtils.copyProperties(img,dto);
            imgList.add(dto);
        }

        ActivityShowInfo showInfo = new ActivityShowInfo();
        BeanUtils.copyProperties(activity,showInfo);
        showInfo.setActivityTypeName(activityTypeName);
        showInfo.setVenueId(venueId);
        showInfo.setVenueName(venueName);
        showInfo.setLikedTime(likedTime);
        showInfo.setActivityImgList(imgList);
        return showInfo;
    }

    //===============likedActList===============
    public List<Integer> usersLikedActsIdList(Integer userId){
        Users user = userRepository.findByUserId(userId);
        List<LikedActivity> likedActivityList = likedRepository.findByUser(user);
        List<Integer> activityIdList =new ArrayList<>();
        if(!likedActivityList.isEmpty()){
            for(LikedActivity act:likedActivityList){
                Integer activityId = act.getVenueActivity().getActivityId();
                activityIdList.add(activityId);
            }
            return activityIdList;
        }else{
            return activityIdList;
        }
    }
    //===============likedOneAct===============
    public Boolean userLikeAnAct(Integer activityId,Integer userId){
        Users users = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        LikedActivity likedActivity = new LikedActivity();
        likedActivity.setUser(users);
        likedActivity.setVenueActivity(activity);
        likedRepository.save(likedActivity);
        return true;
    }

    public Boolean userDislikedAnAct(Integer activityId,Integer userId){
        Users users = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        LikedActivity fromLiked = likedRepository.findByUserAndVenueActivity(users, activity);
        if(fromLiked!=null){
            likedRepository.delete(fromLiked);
            return true;
        }
        return false;
    }

    //===============myFavoriteWall===============
    public List<ActivityBrief> userFavoriteWall(Integer userId){
        Users users = userRepository.findByUserId(userId);
        List<LikedActivity> likedActivityList = likedRepository.findByUser(users);
        List<ActivityBrief> dtoList = new ArrayList<>();
        if(!likedActivityList.isEmpty()){
            for(LikedActivity like:likedActivityList){
                ActivityBrief dto = new ActivityBrief();
                VenueActivity activity = like.getVenueActivity();
                List<LikedActivity> likeList = likedRepository.findByVenueActivity(activity);
                Integer likedTime = likeList.size();

                BeanUtils.copyProperties(activity,dto);
                dto.setLikedTime(likedTime);
                dto.setActivityTypeName(activity.getActivityType().getActivityTypeName());
                dto.setVenueName(activity.getVenue().getVenueName());
                ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
                dto.setGalleryImgUrl(main.getGalleryImgUrl());
                dtoList.add(dto);
            }
            return dtoList;
        }else{
            return null;
        }
    }

    //===============更新主題照片===================
    public Boolean updateTitleImg(Integer activityId,Integer galleryId, MultipartFile file)  {
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        ActivityGallery gallery = galleryRepository.findByGalleryId(galleryId);
        if(gallery!=null){
            try {
                String oldPublicId = gallery.getGalleryPublicId();
                //刪除Cloudinary中的圖片
                Map delResult = cloudinary.uploader().destroy(oldPublicId, ObjectUtils.emptyMap());
                System.out.println("Deleted image from Cloudinary: " + delResult);
                //上傳新圖片
                Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
                //更新資料庫
                gallery.setGalleryImgUrl((String) data.get("url"));
                gallery.setGalleryPublicId((String) data.get("public_id"));
                ActivityGallery save = galleryRepository.save(gallery);
                activity.setActivityUpdateDate(new Date());
                activityRepository.save(activity);
                System.out.println("update main image success " + save.getGalleryImgUrl());
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    //===============更新活動資訊===================
    public Boolean updateMainInfo(Integer activityId,Integer venueId, String activityTitle,
                                  Integer activityDogNumber,Date activityClosingDate,
                                  Date activityDate,Date activityEnd, Date activityStart){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        activity.setVenue(venueRepository.findByVenueId(venueId));
        activity.setActivityTitle(activityTitle);
        activity.setActivityDogNumber(activityDogNumber);
        activity.setActivityClosingDate(activityClosingDate);
        activity.setActivityDate(activityDate);
        activity.setActivityEnd(activityEnd);
        activity.setActivityStart(activityStart);
        activityRepository.save(activity);
        return true;
    }

    //===============更新活動內文(表單)===================
    public Boolean updateDesForm(Integer activityId,String activityProcess,String activityDescription,
                                  String activityCostDescription,String activityNotice){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        activity.setActivityProcess(activityProcess);
        activity.setActivityDescription(activityDescription);
        activity.setActivityCostDescription(activityCostDescription);
        activity.setActivityNotice(activityNotice);
        activityRepository.save(activity);
        return true;
    }
    //===============更新活動內文(表單)===================
    public Boolean updateDesEditor(Integer activityId,String activityDescription){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        activity.setActivityDescription(activityDescription);
        activityRepository.save(activity);
        return true;
    }

    //===============刪除sidePic===================
    public Boolean deleteSideImg(Integer activityId,Integer[] galleryIdList){
        if(galleryIdList.length>0){
            Integer count=0;
            for(Integer galleryId : galleryIdList){
                ActivityGallery gallery = galleryRepository.findByGalleryId(galleryId);
                if (gallery!=null){
                    String publicId = gallery.getGalleryPublicId();
                    try {
                        //刪除Cloudinary中的圖片
                        Map delResult = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
                        System.out.println("Deleted SideImage from Cloudinary: " + delResult);
                        //刪除db的資料
                        galleryRepository.delete(gallery);
                        System.out.println("delete sideImg from db success");
                        count ++;
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            System.out.println("總共刪除: "+count+"張sidePic");
            return true;
        }else{
            return false;
        }
    }
    //===============增加sidePic===================
    public Boolean addSidePic(Integer activityId,MultipartFile file){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        if(activity!=null){
            try {
                //上傳新圖片
                Map data = this.cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder", "activityFolder"));
                ActivityGallery gallery = new ActivityGallery();
                //更新資料庫
                gallery.setVenueActivity(activity);
                gallery.setGalleryImgUrl((String) data.get("url"));
                gallery.setGalleryPublicId((String) data.get("public_id"));
                galleryRepository.save(gallery);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }
    public String addSidePicList(Integer activityId,MultipartFile[] fileList){
        if (fileList.length>0){
            Integer success=0;
            for(MultipartFile file:fileList){
                Boolean result = addSidePic(activityId, file);
                if(result){
                    success++;
                }
            }
            return "成功上傳"+success+"張sidePic!";
        }
        return "根本沒有東西 真是謝囉";
    }

    //================使用者有評論過的activityIDList=============
    public List<Integer> userCommentActivityIdList(Integer userId){
        Users user = userRepository.findByUserId(userId);
        List<Integer> activityIdList=new ArrayList<>();
        List<CommentActivity> venueActivityList = commentRepository.findByUser(user);
        for (CommentActivity comment:venueActivityList){
            Integer activityId = comment.getVenueActivity().getActivityId();
            activityIdList.add(activityId);
        }
        return activityIdList;
    }

    //================使用者個別查看自己寫的內容=============
    public CommentActivity getMyOneComment(Integer userId,Integer activityId){
        Users users = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        return commentRepository.findByUserAndVenueActivity(users,activity);
    }
    //================gemini check==================
    public String geminiCheckComment(String content) {
        Map<String,String> map = new HashMap<>();
        String url = "https://generativelanguage.googleapis.com/v1/models/gemini-pro:generateContent?key=" + apiKey;

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        String contentPrompt =content+"。以上是一句客戶參加我公司所辦理的寵物活動評論，請幫我把句子分類，種類有:正面服務、負面服務、正面場地、負面場地、正面活動、負面活動。若句子為空的，請歸類為無評論。若無法判斷評論是否與活動相關，請歸類為無相關分類。請回傳一個最相關的，若沒有請不要回傳任何值，請用道德高標";
        String requestBody = "{"
                + "\"contents\": [{"
                + "\"parts\": [{\"text\": \"" + contentPrompt + "\"}]"
                + "}],"
                + "\"generationConfig\": {"
                + "\"temperature\": 0.1,"
                + "\"topP\": 0.8,"
                + "\"topK\": 10"
                + "}"
                + "}";

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, requestEntity, String.class);
        String responseBody = responseEntity.getBody();
//        System.out.println(responseBody);
        try {
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray candidates = jsonObject.getJSONArray("candidates");
            if (candidates.length() > 0) {
                JSONObject candidate = candidates.getJSONObject(0);
                JSONObject rsContent = candidate.getJSONObject("content");
                JSONArray parts = rsContent.getJSONArray("parts");
                if (parts.length() > 0) {
                    JSONObject part = parts.getJSONObject(0);
                    System.out.println(part.getString("text"));
                    return part.getString("text");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


    //================使用者個別撰寫評論=============
    public CommentActivity writeOneActivityComment(Integer activityId,Integer userId,String commentText,Integer score){
        Users users = userRepository.findByUserId(userId);
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        CommentActivity commentActivity = new CommentActivity();
        commentActivity.setVenueActivity(activity);
        commentActivity.setUser(users);
        commentActivity.setCommentText(commentText);
        commentActivity.setScore(score);
        String gemini = geminiCheckComment(commentText);
        commentActivity.setCheckResult(gemini);
        return commentRepository.save(commentActivity);
    }

    //================使用者個別更新撰寫評論=============
    public CommentActivity updateOneActivityComment(Integer commentId,String commentText,Integer score){
        CommentActivity comment = commentRepository.findByCommentId(commentId);
        comment.setCommentText(commentText);
        comment.setScore(score);
        String gemini = geminiCheckComment(commentText);
        comment.setCheckResult(gemini);
        return commentRepository.save(comment);
    }

    //================官方找出某活動使用者們的所有評論=============
    public List<ActCommentDto> getOneActAllComments(Integer activityId){
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        List<CommentActivity> commentList = commentRepository.findByVenueActivity(activity);
        List<ActCommentDto> dtoList=new ArrayList<>();
        for(CommentActivity comment:commentList){
            Users user = comment.getUser();
            ActCommentDto dto = new ActCommentDto();
            BeanUtils.copyProperties(comment,dto);
            BeanUtils.copyProperties(activity,dto);
            BeanUtils.copyProperties(user,dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    //================官方找出單個使用者的所有評論=============
    public List<ActCommentDto> findOneUserAllComment(Integer userId){
        Users users = userRepository.findByUserId(userId);
        List<CommentActivity> commentList = commentRepository.findByUser(users);
        List<ActCommentDto> dtoList=new ArrayList<>();
        for(CommentActivity comment:commentList){
            VenueActivity activity = comment.getVenueActivity();
            Users user = comment.getUser();
            ActCommentDto dto = new ActCommentDto();
            BeanUtils.copyProperties(comment,dto);
            BeanUtils.copyProperties(activity,dto);
            BeanUtils.copyProperties(user,dto);
            dtoList.add(dto);
        }
        return dtoList;
    }

    //===========官方寄送參加成功模板信件============
    public void sendJoinSuccessTemplateMail(Integer userId, Integer activityId)  {

        Users user= userRepository.findByUserId(userId);
        String userEmail = user.getUserEmail();
        String firstName = user.getFirstName();
        VenueActivity activity = activityRepository.findByActivityId(activityId);
        String activityTitle = activity.getActivityTitle();
        ActivityGallery main = galleryRepository.findByVenueActivityAndGalleryImgType(activity, "main");
        String imgUrl = main.getGalleryImgUrl();


            System.out.println("hi");
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setFrom("ispaneeit80@gmail.com");
                helper.setTo(userEmail);
                helper.setSubject("Doggy Paradise活動參加成功通知信件");

                Map<String, Object> model = new HashMap<>();
                model.put("userName",firstName);
                model.put("activityTitle",activityTitle);
                model.put("activityId",Integer.toString(activityId));
                model.put("imgUrl",imgUrl);
                String welcomeMail = FreeMarkerTemplateUtils.processTemplateIntoString(freemarkerConfig.getTemplate("ActivityJoinSuccess.html"), model);

                helper.setText(welcomeMail, true);

                mailSender.send(mimeMessage);
            } catch (MessagingException | IOException | TemplateException e) {
                throw new RuntimeException(e);
            }

    }







 }