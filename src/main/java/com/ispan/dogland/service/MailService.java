package com.ispan.dogland.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class MailService {

    @Autowired
    private JavaMailSender mailSender;
    private String emailFrom = "電商官網<a50064xxx@gmail.com>";

    //發送一般文字郵件，receivers:接收帳號(List)、subject:郵件title、content:郵件內文
    public void sendPlainText(Collection<String> receivers, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(receivers.toArray(new String[0]));
        message.setSubject(subject);
        message.setText(content);
        message.setFrom(emailFrom);

        mailSender.send(message);
    }
}
