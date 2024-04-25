package com.ispan.dogland;

import com.ispan.dogland.controller.TweetController;
import com.ispan.dogland.model.dao.DogRepository;
import com.ispan.dogland.model.dao.EmployeeRepository;
import com.ispan.dogland.model.dao.RoomReservationRepository;
import com.ispan.dogland.model.dao.UserRepository;
import com.ispan.dogland.model.dao.product.ProductRepository;
import com.ispan.dogland.model.dao.tweet.*;
import com.ispan.dogland.model.dto.RoomReservationDto;
import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.Employee;
import com.ispan.dogland.model.entity.Users;
//import com.ispan.dogland.model.entity.mongodb.TweetData;
import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductGallery;
import com.ispan.dogland.model.entity.room.Room;
import com.ispan.dogland.model.entity.room.RoomReservation;
import com.ispan.dogland.model.entity.tweet.*;
import com.ispan.dogland.service.ActivityService;
import com.ispan.dogland.service.interfaceFile.AccountService;
import com.ispan.dogland.service.interfaceFile.RoomService;
import com.ispan.dogland.service.interfaceFile.TweetService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@SpringBootApplication
public class DoglandApplication {

	public static void main(String[] args) {
		SpringApplication.run(DoglandApplication.class, args);
	}
	//測試方法
	@Bean
	public CommandLineRunner commandLineRunner(UserRepository userRepository,
											   DogRepository dogRepository,
											   EmployeeRepository employeeRepository,
											   TweetRepository tweetRepository,
											   ProductRepository productRepository,
											   TweetService tweetService,
											   TweetLikeRepository tweetLikeRepository,
											   TweetFollowListRepository tweetFollowListRepository,
											   TweetNotificationRepository tweetNotificationRepository,
											   AccountService accountService,
											   TweetReportRepository tweetReportRepository,
											   RoomReservationRepository roomReservationRepository,
											   RoomService roomService,
											   TweetController tweetController,
											   TweetOfficialRepository tweetOfficialRepository) {
		return runner -> {
		};
	};
}


