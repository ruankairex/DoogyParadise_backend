package com.ispan.dogland.controller;


import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.UserDto;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.service.interfaceFile.AccountService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;


    @GetMapping ("/getUserDetail")
    public ResponseEntity<?> getUserDetail(HttpSession httpSession){
        System.out.println("/getUserDetail 檢查登入 controller");
        Passport loginUser = (Passport) httpSession.getAttribute("loginUser");
        if (loginUser == null) {
            System.out.println("session attribute 空的");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session attribute null"); // 401
        }
        Users userDetail = accountService.getUserDetailByMail(loginUser.getEmail());
        UserDto uto = accountService.packUserIntoDtoWithoutSensitiveData(userDetail);
        if (userDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User detail not found"); // 404
        }
        return ResponseEntity.ok(uto);
    }

    @GetMapping ("/getUserPassport")
    public ResponseEntity<?> getUserPassport(HttpSession httpSession){
        System.out.println("/getUserDetail 檢查登入 controller");

        Passport loginUser = (Passport) httpSession.getAttribute("loginUser");
        if (loginUser == null) {
            System.out.println("session attribute 空的");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("session attribute null"); // 401
        }

        Users userDetail = accountService.getUserDetailByMail(loginUser.getEmail());
        if (userDetail == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User detail not found"); // 404
        }

        return ResponseEntity.ok(loginUser);
    }


    @GetMapping("/api/users/map")
    public ResponseEntity<?> testSessionValue(HttpSession httpSession) {
        System.out.println("檢查登入 controller:");

        Passport loginUser = (Passport) httpSession.getAttribute("loginUser");
        System.out.println("檢查登入 controller:"+loginUser);

        if (loginUser == null) {
            System.out.println("session attribute 空的");
            return new ResponseEntity<String>("session attribute null", HttpStatus.UNAUTHORIZED); // 401
        }
        //更新登入時間
        Users userDetail = accountService.getUserDetailById(loginUser.getUserId());
        userDetail.setLastLoginTime(new Date());
        accountService.updateUser(userDetail);

        return new ResponseEntity<Passport>(loginUser, HttpStatus.OK);
    }


    @PostMapping("/formLogin")
    public ResponseEntity<Object> formLogin(@RequestParam String email, @RequestParam String password, HttpSession session) {

        Passport loginUser = accountService.loginCheck(email, password);
        if (loginUser == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("登入失敗");//401
        } else {
            session.setAttribute("loginUser", loginUser);
            return ResponseEntity.ok(loginUser);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?>   registerUser(@RequestBody Users userData,HttpSession httpSession){
        String useremail = userData.getUserEmail();

        //檢查信箱是有重複
        if(accountService.checkEmailIsEmpty(useremail)){
            Users user = accountService.register(userData);
            System.out.println("註冊成功");

            // set session
            Passport loginUser = new Passport(user.getLastName(),user.getUserEmail(), user.getUserId(), user.getUserStatus());
            httpSession.setAttribute("loginUser",loginUser);

            return new ResponseEntity<String>("註冊成功", HttpStatusCode.valueOf(200));
        }

        return new ResponseEntity<String>("註冊失敗", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping("/check")
    public boolean checkLogin(HttpSession session) {
        Passport loggedInMember = (Passport) session.getAttribute("loginUser");
        System.out.println("check = " + loggedInMember);
        return !Objects.isNull(loggedInMember);
    }

    @RequestMapping("/logout")
    public boolean logout(HttpSession session) {
        session.invalidate();
        System.out.println("logout");
        return true;
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<?> usersLogout(HttpSession httpSession){

        System.out.println("登出 controller");

        httpSession.removeAttribute("loginUser");

        return new ResponseEntity<String>("logout success", HttpStatusCode.valueOf(200));
    }


    //忘記密碼:發送驗證碼到使用者Email
    @GetMapping("/forgetPassword/sendCode/{userEmail}")
    public ResponseEntity<String> sendCode(@PathVariable("userEmail") String userEmail) {
        if (!accountService.checkEmailIsEmpty(userEmail)) {
            accountService.sendCodeForResetPassword(userEmail);
            return ResponseEntity.ok("已發送驗證碼至信箱");
        }
        return ResponseEntity.badRequest().body("信箱有誤");
    }

    //忘記密碼:驗證驗證碼與Email真偽
    @PostMapping("/forgetPassword/verifyCode")
    public ResponseEntity<String> verifyCode(@RequestBody Map<String,String> data) {
        String email = data.get("email");
        String verifyCode = data.get("verifyCode");
        boolean isValid = accountService.verifyCodeForResetPassword(email, verifyCode);
        if (isValid) {
            // 驗證碼正確，導向重設密碼頁面
            return ResponseEntity.ok("verifyCode pass");
        } else {
            // 驗證碼錯誤
            return ResponseEntity.badRequest().body("verifyCode failed");
        }
    }

    //忘記密碼:重新設定密碼
    @PostMapping("/forgetPassword/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody)  {
        String verifyCode = requestBody.get("verifyCode");
        String email = requestBody.get("email");
        String newPassword = requestBody.get("newPassword");

        boolean isValid = accountService.verifyCodeForResetPassword(email, verifyCode);
        if (isValid) {
            accountService.resetPassword(email, newPassword);
            accountService.clearVerificationCode(email);
            return ResponseEntity.ok("resetPassword success");
        }
        return ResponseEntity.badRequest().body("resetPassword failed");
    }

    //會員系統，使用者更新資料
    @PostMapping("/account/update")
    public ResponseEntity<Passport> updateAccount(@RequestBody Users user,HttpSession session) {

        //更新資料庫
        Users realUser = accountService.getUserDetailById(user.getUserId());
        realUser.setLastName(user.getLastName());
        realUser.setBirthDate(user.getBirthDate());
        realUser.setUserGender(user.getUserGender());
        accountService.updateUser(realUser);

        //更新session
        Passport loginUser = accountService.updateLoginUser(session, realUser);
        return ResponseEntity.ok(loginUser);
    }

    @PostMapping("/account/addMainImg")
    public String addMainImg(@RequestParam Integer userId, @RequestParam MultipartFile mainImg, HttpSession session) {
        //更新資料庫
        String imgURL= accountService.uploadImg(mainImg,userId);
        //更新session
        Passport loginUser = accountService.updateImgPathFromLoginUser(session, imgURL);
        return imgURL;
    }

    @PostMapping("/account/updatePassword")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> requestBody,HttpSession session) {
        String oldPassword = requestBody.get("oldPassword");
        String newPassword = requestBody.get("newPassword");


        Passport loginUser=(Passport)session.getAttribute("loginUser");
        Users user = accountService.findUsersByUserId(loginUser.getUserId());
        //google登入後是無密碼狀態
        if(user.getUserPassword()==null || user.getUserPassword().isEmpty()){
            accountService.resetPassword(user.getUserEmail(), newPassword);
            return ResponseEntity.ok("resetPassword success");
        }
        if(accountService.loginCheck(loginUser.getEmail(), oldPassword) != null){
            accountService.resetPassword(loginUser.getEmail(), newPassword);
            return ResponseEntity.ok("resetPassword success");
        }
        return ResponseEntity.badRequest().body("舊密碼有誤");
    }


    @GetMapping("/account/checkPasswordIsEmpty")
    public boolean checkPasswordIsEmpty(HttpSession session) {
        Passport loginUser=(Passport)session.getAttribute("loginUser");
        Users user = accountService.findUsersByUserId(loginUser.getUserId());
        return accountService.checkPasswordIsEmpty(user);
    }




}
