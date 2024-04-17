package com.ispan.dogland.aspect;

import com.ispan.dogland.model.dto.Passport;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;
import java.util.logging.Logger;

@Aspect
@Component
public class AuthorityAspect {

    private Logger myLogger = Logger.getLogger(getClass().getName());
    @Pointcut("execution(* com.ispan.dogland.controller.TweetController.*(..))")
    private void forTweetControllerPackage(){}
    @Pointcut("execution(* com.ispan.dogland.controller.TweetShareController.*(..))")
    private void forTweetShareControllerPackage(){}
    @Pointcut("execution(* com.ispan.dogland.controller.RoomController.*(..))")
    private void forRoomControllerPackage(){}
//    @Pointcut("execution(* com.ispan.dogland.controller..*(..))")
//    private void forShopControllerPackage(){}
//    @Pointcut("execution(* com.ispan.dogland.controller..*(..))")
//    private void forActivityControllerPackage(){}


    @Pointcut("forTweetControllerPackage() || forTweetShareControllerPackage() || forRoomControllerPackage()")// 加上||方法名(要加or)
    private void forAppFlow(){}



    @Before("forAppFlow()")
    public void before() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();
        Passport passport = (Passport)session.getAttribute("loginUser");
        if(passport == null) {
            myLogger.warning("沒有登入");
            throw new RuntimeException("沒有登入");
        }

    }

}
