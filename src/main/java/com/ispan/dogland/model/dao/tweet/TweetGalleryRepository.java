package com.ispan.dogland.model.dao.tweet;

import com.ispan.dogland.model.entity.tweet.TweetGallery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TweetGalleryRepository extends JpaRepository<TweetGallery, Integer> {
}
