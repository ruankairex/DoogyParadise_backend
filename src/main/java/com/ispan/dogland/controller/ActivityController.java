package com.ispan.dogland.controller;

import com.cloudinary.utils.ObjectUtils;
import com.ispan.dogland.model.dto.*;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.activity.*;
import com.ispan.dogland.service.ActivityService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping("/activity/api")
@CrossOrigin(allowCredentials = "true",origins = {"http://localhost:5137","http://127.0.0.1:5173"})
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //===============列出所有venue==================
    @GetMapping("/venues")
    public List<Venue> allVenue(){
        return activityService.getAllVenue();
    }

    //===============新增活動類型===================

    @PostMapping("/type/add")
    public ActivityType createType(@RequestParam String activityType , HttpSession httpSession){
        //**之後再補只有員工能新增

        ActivityType type = activityService.searchType(activityType);
        if(activityType!=null && type==null) {
            return activityService.createType(activityType);
        }
        throw new RuntimeException("已有此活動類別");
    }

    //===============列出所有活動類型===================
    @GetMapping("/types")
    public List<ActivityType> allActivityType(){
        return activityService.allActivityType();
    }

    //===============新增場地租借===================

    @PostMapping("/rental/add")
    public RentalData createNewRental(@RequestBody RentalData rentalData){
        //**要再新增從session撈資料
        return activityService.addNewRental(rentalData);

    }
    @PostMapping("/rental/officialAdd")
    public RentalData addOfficialRental(@RequestBody RentalData rentalData){
        //**要再新增從session撈資料
        return activityService.addOfficialRental(rentalData);

    }
    //===============查詢場地租借===================
    @GetMapping("/rentals/{pageNumber}")
    public Page<RentalData> findRentalByPage(@PathVariable Integer pageNumber){
        return activityService.findRentalByPage(pageNumber);
    }
    //===============查詢使用者場地租借===================
    @GetMapping("/myRentals/{pageNumber}")
    public Page<RentalData> findRentalByPage(@PathVariable Integer pageNumber,
                                             HttpSession httpSession){
        //**session取得使用者id
        return activityService.findUserRetalByPage(1,1);
    }
    //===============官方新增活動===================

    @PostMapping("/official/addActivity")
    public ActivityData addNewActivity(@RequestBody ActivityData activityData){
        return activityService.addNewActivity(activityData);
    }
    @PostMapping("/official/createActivity")
    public ActivityCreateDto createNewActivity(@RequestBody ActivityCreateDto createDto ){
        return activityService.offCreateNewActivity(createDto);
    }

    @PostMapping("/official/addMainImg")
    public ActivityGallery addMainImg(@RequestParam Integer activityId,@RequestParam MultipartFile mainImg){
        return activityService.addTitleImg(mainImg,activityId);
    }

    @PostMapping("/official/addNormalImg")
    public List<ActivityGallery> addNormalImg(@RequestParam Integer activityId,@RequestParam MultipartFile[] normalImages){
        List<ActivityGallery> uploadedImages=new ArrayList<>();
        for(MultipartFile img:normalImages){
            ActivityGallery gallery = activityService.addNormalImg(img, activityId);
            uploadedImages.add(gallery);
        }
        return uploadedImages;
    }

    //===============查詢所有活動===================
    @GetMapping("/all/{pageNumber}")
    public Page<ActivityBrief> showBriefByPage(@PathVariable Integer pageNumber){
        return activityService.findActivityByPage(pageNumber);
    }


    //===============查詢使用者的狗===================
    @GetMapping("/searchUsersDog/{userId}")
    public List<Dog> findUserDogs(@PathVariable Integer userId){
        return activityService.findUsersDog(userId);
    }

    //===============使用者與狗狗報名活動===============
    @PostMapping("/JoinActivity")
    public List<ApplyData> userDogsJoin(@RequestParam Integer userId,
                                        @RequestParam String note,
                                        @RequestParam Integer[] dogIdList,
                                        @RequestParam Integer activityId){
        VenueActivity activity = activityService.findActivityByActivityId(activityId);
        Integer activityDogNumber = activity.getActivityDogNumber();
        Integer currentDogNumber = activity.getCurrentDogNumber();
        Integer length = dogIdList.length;
        if(activityDogNumber>=currentDogNumber+length){
            ActivityUserJoined userJoined = activityService.userApply(userId, note,activityId);
            List<ApplyData> joinedMembers=new ArrayList<>();
            for(Integer dogId:dogIdList){
                ActivityDogJoined dogJoined = activityService.dogApply(dogId, activityId);
                ApplyData applyData = new ApplyData();
                BeanUtils.copyProperties(userJoined,applyData);
                BeanUtils.copyProperties(dogJoined,applyData);
                applyData.setUserId(userJoined.getUser().getUserId());
                applyData.setActivityId(dogJoined.getVenueActivity().getActivityId());
                applyData.setActivityDate(dogJoined.getVenueActivity().getActivityDate());
                applyData.setDogName(dogJoined.getDog().getDogName());
                applyData.setActivityTitle(dogJoined.getVenueActivity().getActivityTitle());
                joinedMembers.add(applyData);
            }
            activityService.sendJoinSuccessTemplateMail(userId,activityId);
            return joinedMembers;
        }
        throw new RuntimeException("超過狗數限制 !!");
    }


    @GetMapping("/apply/{userId}/dogNotJoinedList/{activityId}")
    public List<Dog> findUserDogNotInThisActivity(@PathVariable Integer userId,@PathVariable Integer activityId){
        return activityService.findUserDogsStayAtHome(userId, activityId);
    }

    @GetMapping("/activityManager/{userId}/findDogListIn/{activityId}")
    public List<Dog> findUserDogsAttendThisActivity(@PathVariable Integer userId,@PathVariable Integer activityId){
        return activityService.findUserDogsAttendThisActivity(userId, activityId);
    }

    @GetMapping("/activityManager/{userId}")
    public List<MyActivitiesDto>findUserAllJoinedActivities(@PathVariable Integer userId){
        return activityService.findUserAllJoinedActivities(userId);
    }

    //===============使用者活動管理頁面取消報名===============
    @PostMapping ("/activityManager/doCancelProcess")
    public MyActivitiesDto userCancelledActivity(@RequestParam Integer userId,
                                                 @RequestParam Integer[] dogIdList,
                                                 @RequestParam Integer activityId){
        return activityService.userCancelledActivity(userId,dogIdList,activityId);
    }

    //===============使用者活動管理頁面更改備註===============
    @PostMapping ("/activityManager/doRenewNoteProcess")
    public ActivityUserJoined renewUserNote(@RequestParam Integer activityId,
                                            @RequestParam Integer userId,
                                            @RequestParam String userNote){
        return activityService.renewUserNote(activityId,userId,userNote);
    }

    //===============請求員工資料===============
    @GetMapping("/official/employeePass/{employeeId}")
    public EmployeeDto getEmployeePass(@PathVariable Integer employeeId){
        return activityService.getEmployeePass(employeeId);
    }

    //===============所有過去活動===============
    @GetMapping("/allPastAct/{pageNumber}")
    public Page<ActivityBrief> showPastBriefByPage(@PathVariable Integer pageNumber){
        return activityService.findPastActivityByPage(pageNumber);
    }

    //===============類別過去活動===============
    @GetMapping("/pastAct/category/{typeId}/{pageNumber}")
    public Page<ActivityBrief> showPastBriefByPage(@PathVariable Integer pageNumber,@PathVariable Integer typeId){
        return activityService.findPastActByCategory(pageNumber,typeId);
    }

    //===============所有過去活動===============
    @GetMapping("/allNowAct/{pageNumber}")
    public Page<ActivityBrief> showNowBriefByPage(@PathVariable Integer pageNumber){
        return activityService.findNowActivityByPage(pageNumber);
    }

    //===============類別過去活動===============
    @GetMapping("/nowAct/category/{typeId}/{pageNumber}")
    public Page<ActivityBrief> showNowBriefByPage(@PathVariable Integer pageNumber,@PathVariable Integer typeId){
        return activityService.findNowActByCategory(pageNumber,typeId);
    }

    //===============官方管理頁面===============
    @GetMapping("/official/activityManager/past")
    public List<ActivityPastBrief> findPastActivityInThisPeriod(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 今天
        Date endDate = calendar.getTime();
        System.out.println("activityManager/past執行今天：" + endDate);

        // 上個月的今日
        calendar.add(Calendar.MONTH, -1);
        Date startDate = calendar.getTime();
        System.out.println("activityManager/past執行上個月開始：" + startDate);
        return activityService.officialActManagerByStatus(startDate,endDate);
    }
    @PostMapping("/official/activityManager/past")
    public List<ActivityPastBrief> findPastActivityInThisPeriod(@RequestParam Date startDate,@RequestParam Date endDate){
        return activityService.officialActManagerByStatus(startDate,endDate);
    }

    @GetMapping("/official/activityManager/now")
    public List<ActivityBrief> findNowActivityInThisPeriod(){
        Date currentDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        // 今天是第一天
        Date startDate = calendar.getTime();
        System.out.println("activityManager/now執行今天：" + startDate);

        // 下個月的同一天
        calendar.add(Calendar.MONTH, 1);
        Date endDate = calendar.getTime();
        System.out.println("activityManager/now執行下個月的同日：" + endDate);
        return activityService.officialActManagerByStatusNot(startDate,endDate);
    }


    @PostMapping("/official/activityManager/now")
    public List<ActivityBrief> findNowActivityInThisPeriod(@RequestParam Date startDate,@RequestParam Date endDate){
        return activityService.officialActManagerByStatusNot(startDate,endDate);
    }

    @GetMapping("/official/activityManager/{activityId}/attendeeList")
    public List<ActOfficialAttendeeDto> findOnePastActAttendeeList (@PathVariable Integer activityId){
        return activityService.findOneActAttendeeList(activityId,"已取消");
    }

    @GetMapping("/official/activityInfo/{activityId}")
    public ActivityShowInfo getActivityInfo(@PathVariable Integer activityId){
        return activityService.getActivityPageInfo(activityId);
    }

    @GetMapping("/usersLiked/{userId}")
    public List<Integer> getUsersLikedActIds(@PathVariable Integer userId){
        return activityService.usersLikedActsIdList(userId);
    }
    @PostMapping("/userDo/like")
    public Boolean userLikeAct(@RequestParam Integer activityId,@RequestParam Integer userId){
        return activityService.userLikeAnAct(activityId,userId);
    }

    @PostMapping("/userDo/dislike")
    public Boolean userDislikeAct(@RequestParam Integer activityId,@RequestParam Integer userId){
        return activityService.userDislikedAnAct(activityId,userId);
    }

    @GetMapping("/likedManager/{userId}")
    public List<ActivityBrief> userFavoriteWall(@PathVariable Integer userId){
        return activityService.userFavoriteWall(userId);
    }

    @PostMapping("/official/updateManager/mainPic")
    public boolean OffUpdateMainImg(@RequestParam Integer activityId,
                                    @RequestParam Integer galleryId,
                                    @RequestParam MultipartFile mainImg){
        return activityService.updateTitleImg(activityId,galleryId,mainImg);
    }
    @PostMapping("/official/updateManager/Info")
    public Boolean updateMainInfo(@RequestParam Integer activityId,@RequestParam Integer venueId, @RequestParam String activityTitle,
                                     @RequestParam Integer activityDogNumber,@RequestParam Date activityClosingDate,
                                     @RequestParam Date activityDate,@RequestParam Date activityEnd, @RequestParam Date activityStart){
        return activityService.updateMainInfo(activityId,venueId,activityTitle,activityDogNumber,activityClosingDate,activityDate,activityEnd,activityStart);
    }

    @PostMapping("/official/updateManager/desForm")
    public Boolean updateMainInfo(@RequestParam Integer activityId,@RequestParam String activityProcess,@RequestParam String activityDescription,
                                  @RequestParam String activityCostDescription,@RequestParam String activityNotice){
        return activityService.updateDesForm(activityId,activityProcess,activityDescription,activityCostDescription,activityNotice);
    }
    @PostMapping("/official/updateManager/desCkeditor")
    public Boolean updateDesEditor(Integer activityId,String activityDescription){
        return activityService.updateDesEditor(activityId,activityDescription);
    }

    @PostMapping("/official/updateManager/sidePic/delete")
    public Boolean deleteSidePic(Integer activityId,Integer[] delGalleryIdList){
       return activityService.deleteSideImg(activityId,delGalleryIdList);
    }

    @PostMapping("/official/updateManager/sidePic/add")
    public String addSidePic(Integer activityId,MultipartFile[] addSidePicList){
        return activityService.addSidePicList(activityId,addSidePicList);
    }

    //得到哪些活動評論過
    @GetMapping("/activityManager/get/{userId}/commentList")
    public List<Integer> getMyCommentedList(@PathVariable Integer userId){
        return activityService.userCommentActivityIdList(userId);
    }
    //使用者個別查看自己寫的內容
    @GetMapping("/activityManager/get/{userId}/comment/{activityId}")
    public CommentActivity getMyOneComment(@PathVariable Integer userId,@PathVariable Integer activityId){
        return activityService.getMyOneComment(userId,activityId);
    }

    //上傳自己的評論
    @PostMapping("/activityManager/doComment")
    public CommentActivity writeActivityComment(@RequestParam Integer activityId,@RequestParam Integer userId,
                                                @RequestParam String commentText,@RequestParam Integer score){
        return activityService.writeOneActivityComment(activityId, userId, commentText, score);
    }
    //修改自己的評論
    @PostMapping("/activityManager/doComment/update")
    public CommentActivity updateActivityComment(@RequestParam Integer commentId,@RequestParam String commentText,
                                                 @RequestParam Integer score){
        return activityService.updateOneActivityComment(commentId, commentText, score);
    }

    //官方找出某活動使用者們的所有評論
    @GetMapping("official/activityManager/past/commentList/{activityId}")
    public List<ActCommentDto> getOneActAllComments(@PathVariable Integer activityId){
        return activityService.getOneActAllComments(activityId);
    }
    //官方找出某使用者的所有評論
    @GetMapping("official/activityManager/past/{userId}/allComment")
    public List<ActCommentDto> findOneUserAllComment(@PathVariable Integer userId){
        return activityService.findOneUserAllComment(userId);
    }

}
