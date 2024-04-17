package com.ispan.dogland.model.dao.activity;

import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.activity.VenueRental;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VenueRentalRepository extends JpaRepository<VenueRental,Integer> {

    VenueRental findByRentalId(Integer rentalId);

    Page<VenueRental> findByUser(Users user, Pageable pageable);

    Page<VenueRental> findByUserNull(Pageable pageable);


}
