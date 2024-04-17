package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.ActivityUserJoined;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityUserJoinedRepository extends JpaRepository<ActivityUserJoined,Integer> {
    ActivityUserJoined findByUserJoinedId (Integer userJoinedId);

    ActivityUserJoined findByVenueActivityAndUser(VenueActivity venueActivity, Users user);

    ActivityUserJoined findByVenueActivityAndUserAndJoinedStatusNot(VenueActivity venueActivity, Users user,String joinedStatus);

    //找出所有使用者有參加/有取消的活動
    List<ActivityUserJoined> findByUserAndJoinedStatusNot(Users user,String joinedStatus);

    List<ActivityUserJoined> findByVenueActivityAndJoinedStatusNot(VenueActivity venueActivity,String joinStatus);
}
