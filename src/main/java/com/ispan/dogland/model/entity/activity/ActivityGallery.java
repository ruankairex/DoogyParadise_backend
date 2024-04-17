package com.ispan.dogland.model.entity.activity;

import jakarta.persistence.*;

@Entity
@Table
public class ActivityGallery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer galleryId;
    @ManyToOne
    @JoinColumn(name = "activity_id")
    private VenueActivity venueActivity;
    private String galleryImgUrl;
    private String galleryPublicId;
    private String galleryImgType;

    public ActivityGallery() {
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public VenueActivity getVenueActivity() {
        return venueActivity;
    }

    public void setVenueActivity(VenueActivity venueActivity) {
        this.venueActivity = venueActivity;
    }

    public String getGalleryImgUrl() {
        return galleryImgUrl;
    }

    public void setGalleryImgUrl(String galleryImgUrl) {
        this.galleryImgUrl = galleryImgUrl;
    }

    public String getGalleryPublicId() {
        return galleryPublicId;
    }

    public void setGalleryPublicId(String galleryPublicId) {
        this.galleryPublicId = galleryPublicId;
    }

    public String getGalleryImgType() {
        return galleryImgType;
    }

    public void setGalleryImgType(String galleryImgType) {
        this.galleryImgType = galleryImgType;
    }
}
