package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.Dog;
import com.ispan.dogland.model.entity.activity.ActivityDogJoined;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActivityDogJoinedRepository extends JpaRepository<ActivityDogJoined,Integer> {
    ActivityDogJoined findByDogJoinedId(Integer dogJoinedId);

    //想要知道某隻狗有沒有報名過這個活動
    ActivityDogJoined findByVenueActivityAndDog(VenueActivity venueActivity,Dog dog);

    ActivityDogJoined findByVenueActivityAndDogAndJoinedStatusNot(VenueActivity venueActivity,Dog dog,String joinedStatus);

    //想要知道這個活動某個使用者的哪些狗們有參加

    //想要知道這個活動某個使用者的哪些狗沒有參加!
}
