package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.entity.Users;
import org.springframework.web.multipart.MultipartFile;


public interface AccountService {

    public Passport loginCheck(String email, String password);
    public boolean checkEmailIsEmpty(String email);

    public Users register(Users user);

    public Users getUserDetail(String email);

    public Users getUserDetailById(Integer userId);

    public Passport getPassportFromFormLogin(String username);


    public String encodePassword(String password);

    public void sendCodeForResetPassword(String email);

    public boolean verifyCodeForResetPassword(String email, String code);

    public void resetPassword(String email, String newPassword);

    public void clearVerificationCode(String email);

    public void updateUser(Users user);

    public Users findUsersByLastName(String lastName);

    public String uploadImg(MultipartFile file, Integer userId);

    public Users findUsersByUserId(Integer userId);

    public Users findUsersByTweetId(Integer tweetId);

}
