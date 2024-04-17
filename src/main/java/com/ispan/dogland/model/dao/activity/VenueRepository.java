package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.activity.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue,Integer> {

    Venue findByVenueId(Integer venueId);

    Venue findByVenueName(String venueName);
}
