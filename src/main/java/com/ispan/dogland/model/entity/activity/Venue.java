package com.ispan.dogland.model.entity.activity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Table
@Entity
public class Venue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer venueId;
    private String venueName;
    private Integer venueCapacityDog;
    private Integer venueCapacityUser;
    private String venueDescription;
    private Integer venueRent;

    //////////////////////////////////////
    @JsonIgnore
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VenueActivity> venueActivityList;

    @JsonIgnore
    @OneToMany(mappedBy = "venue", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<VenueRental> venueRentalList;


    /////////////////////////////////////
    public Venue() {
    }

    //////////////////////////////////////
    public Integer getVenueId() {
        return venueId;
    }

    public void setVenueId(Integer venueId) {
        this.venueId = venueId;
    }

    public String getVenueName() {
        return venueName;
    }

    public void setVenueName(String venueName) {
        this.venueName = venueName;
    }

    public Integer getVenueCapacityDog() {
        return venueCapacityDog;
    }

    public void setVenueCapacityDog(Integer venueCapacityDog) {
        this.venueCapacityDog = venueCapacityDog;
    }

    public Integer getVenueCapacityUser() {
        return venueCapacityUser;
    }

    public void setVenueCapacityUser(Integer venueCapacityUser) {
        this.venueCapacityUser = venueCapacityUser;
    }

    public String getVenueDescription() {
        return venueDescription;
    }

    public void setVenueDescription(String venueDescription) {
        this.venueDescription = venueDescription;
    }

    public Integer getVenueRent() {
        return venueRent;
    }

    public void setVenueRent(Integer venueRent) {
        this.venueRent = venueRent;
    }

    public List<VenueActivity> getVenueActivityList() {
        return venueActivityList;
    }

    public void setVenueActivityList(List<VenueActivity> venueActivityList) {
        this.venueActivityList = venueActivityList;
    }
}
