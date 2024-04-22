package com.ispan.dogland.service.interfaceFile;

import com.ispan.dogland.model.dto.Passport;
import com.ispan.dogland.model.dto.UserDto;
import com.ispan.dogland.model.entity.Users;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.multipart.MultipartFile;


public interface AccountService {

    public Passport loginCheck(String email, String password);

    public boolean checkEmailIsEmpty(String email);

    public boolean checkPasswordIsEmpty(Users user);

    public Users register(Users user);

    public Users getUserDetailByMail(String email);

    public Users getUserDetailById(Integer userId);

    public Passport getPassportFromFormLogin(String username);

    public String encodePassword(String password);

    public void sendCodeForResetPassword(String email);

    public boolean verifyCodeForResetPassword(String email, String code);

    public void resetPassword(String email, String newPassword);

    public void clearVerificationCode(String email);

    public void updateUser(Users user);

    public Passport updateLoginUser(HttpSession session, Users user);

    public Passport updateImgPathFromLoginUser(HttpSession session, String imgURL);

    public Users findUsersByLastName(String lastName);

    public String uploadImg(MultipartFile file, Integer userId);

    public Users findUsersByUserId(Integer userId);

    public Users findUsersByTweetId(Integer tweetId);

    public UserDto packUserIntoDtoWithoutSensitiveData(Users user);

}
