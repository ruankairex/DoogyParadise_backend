package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.LikedActivity;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedActivityRepository extends JpaRepository<LikedActivity,Integer> {
    LikedActivity findByLikedActivityId(Integer likedActivityId);
    List<LikedActivity> findByUser(Users user);

    List<VenueActivity> findVenueActivityByUser(Users user);

    LikedActivity findByUserAndVenueActivity(Users users, VenueActivity venueActivity);

    List<LikedActivity> findByVenueActivity(VenueActivity venueActivity);
}
