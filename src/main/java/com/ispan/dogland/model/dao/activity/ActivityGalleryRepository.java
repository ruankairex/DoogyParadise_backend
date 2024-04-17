package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.ActivityGallery;
import com.ispan.dogland.model.entity.activity.VenueActivity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ActivityGalleryRepository extends JpaRepository<ActivityGallery,Integer> {
    ActivityGallery findByGalleryId(Integer galleryId);
    ActivityGallery findByVenueActivityAndGalleryImgType(VenueActivity activity,String type);
    List<ActivityGallery> findByVenueActivity(VenueActivity activity);
}
