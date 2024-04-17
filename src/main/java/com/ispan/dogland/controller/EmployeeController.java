package com.ispan.dogland.controller;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.RoomReservationDto;
import com.ispan.dogland.model.dto.ScoreDto;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.OrderCancel;
import com.ispan.dogland.model.entity.mongodb.TweetData;
import com.ispan.dogland.model.entity.product.ProductCategory;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.tweet.Tweet;
import com.ispan.dogland.service.EmployeeService;
import com.ispan.dogland.service.interfaceFile.RoomService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employee")
@CrossOrigin(allowCredentials = "true", origins = { "http://localhost:5173/", "http://127.0.0.1:5173" })
public class EmployeeController {
    @Autowired
    private EmployeeService es;
    @Autowired
    private RoomService rService;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    private TweetService tweetService;

    @Autowired
    private RestTemplate restTemplate;


    @GetMapping("/roomReservation")
    public List<RoomReservationDto> reservation(){
        return rService.findAllRoomReservation();
    }

    @GetMapping("/score")
    public List<ScoreDto> score(){
        return rService.findAllScore();
    }

    @PostMapping("/addRoomImg")
    public String addRoomImg(Integer roomId, @RequestParam MultipartFile roomImgPath) {
        System.out.println("addRoomImg");
        System.out.println(roomId);
        System.out.println(roomImgPath);
        return rService.uploadImg(roomId, roomImgPath);
    }

    @PostMapping("/updateRoom")
    public Integer updateRoom(@RequestBody Room room, @RequestParam Integer roomId) {
        rService.updateRoom(room, roomId);
        return roomId;
    }

    @GetMapping("/banTweet/{tweetId}/{reportsId}")
    public String banTweet(@PathVariable Integer tweetId, @PathVariable Integer reportsId, HttpSession session) {
        //banTweet
        Tweet tweet = tweetService.banTweet(tweetId);
        //送通知
        Integer userId = tweet.getUser().getUserId();
        tweetService.sendBanTweetNotificationToUser(userId,tweet);
        //檢舉單號加入員工
        Passport emp = (Passport)session.getAttribute("loginUser");
        tweetService.addEmployeeToReport(reportsId,emp.getUserId());
        return emp.getUsername();
    }

    @GetMapping("/noBanTweet/{tweetId}/{reportsId}")
    public String noBanTweet(@PathVariable Integer tweetId, @PathVariable Integer reportsId, HttpSession session) {
        //檢舉單號加入員工
        Passport emp = (Passport)session.getAttribute("loginUser");
        tweetService.addEmployeeToReport(reportsId,emp.getUserId());
        return emp.getUsername();
    }

    @GetMapping("/getEmpByReportId/{reportId}")
    public Employee getEmpByReportId(@PathVariable Integer reportId) {
        return tweetService.findEmployeeByReportId(reportId);
    }
    @GetMapping("/getCategory")
    public List<ProductCategory> getCategory(){
        return es.findCategories();
    }



    @GetMapping("/sendTweetContentsToFlask")
    @Async
    public String sendToFlask() {
        // Flask 應用程式的 URL
        String flaskUrl = "http://localhost:5000/api/process_sentence";
        // 設置headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // 準備請求內容
        List<Tweet> tweets = tweetService.findAllTweetsOnly();
        List<String> sentences = new ArrayList<>();
        for (Tweet tweet : tweets) {
            sentences.add(tweet.getTweetContent());
        }
        // 創建 ObjectMapper 實例
        ObjectMapper objectMapper = new ObjectMapper();
        // 將 sentences 轉換為 JSON 字符串
        String sentencesJson;
        try {
            sentencesJson = objectMapper.writeValueAsString(sentences);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // 處理序列化時的異常
            return "Error occurred during JSON serialization";
        }
        String requestBody = "{\n" +
                "    \"sentences\": " + sentencesJson + "\n" +
                "}";
        // 發送 POST 請求到 Flask 應用程式
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);
        String result = restTemplate.postForObject(flaskUrl, requestEntity, String.class);
        // 回傳 Flask 的回應
        return result;
    }


    @GetMapping("/getTweetData")
    public TweetData getTweetData() {
        return tweetService.getLastTweetData();
    }



    @PostMapping("/addProduct")
    public ResponseEntity<String> addProduct(@RequestParam("productName") String productName,
                                             @RequestParam("categoryId") Integer categoryId,
                                             @RequestParam("productPrice") Integer unitPrices,
                                             @RequestParam("productDescription") String productDescription,
                                             @RequestParam("stock") Integer stock,
                                             @RequestParam("productImage") MultipartFile productImage,
                                             HttpSession hsession){
        try {
            Map cloudData = this.cloudinary.uploader().upload(productImage.getBytes(), ObjectUtils.asMap("folder","product"));
            String url = (String) cloudData.get("url");

            es.addNewProduct(productName,categoryId,unitPrices,productDescription,stock,url);

            return ResponseEntity.ok("Product上架成功");
        } catch (IOException e) {
            throw new RuntimeException(e);

        }
    }

    @GetMapping("/showOrderCase")
    public List<OrderCancel> getCase() {
        return es.findOrderCases();
    }

    @PostMapping("/confirmOrderCancel")
    public ResponseEntity<String> doCancel(@RequestParam("orderId") Integer orderId){
        es.updateCancelOrder(orderId);
        return ResponseEntity.ok("success");
    }
}