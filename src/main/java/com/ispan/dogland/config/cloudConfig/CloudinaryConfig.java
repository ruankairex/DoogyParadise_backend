package com.ispan.dogland.config.cloudConfig;

import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


@Configuration
public class CloudinaryConfig {


    @Bean
    public Cloudinary getCloudinary(){
        Map config = new HashMap();
        config.put("cloud_name","dxz9qtntt");
        config.put("api_key","343193624619149");
        config.put("api_secret","vqwpNIH-xiQ1mmkM4ZizeMgpaPI");
        config.put("secure",true);
        return  new Cloudinary(config);
    }
}
