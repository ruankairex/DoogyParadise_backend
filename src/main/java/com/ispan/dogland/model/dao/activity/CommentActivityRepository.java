package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.CommentActivity;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentActivityRepository extends JpaRepository<CommentActivity,Integer> {
    List<CommentActivity> findByVenueActivity(VenueActivity venueActivity);
    CommentActivity findByCommentId(Integer commentId);
    List<CommentActivity> findByUser(Users users);
    CommentActivity findByUserAndVenueActivity(Users user,VenueActivity venueActivity);


}
