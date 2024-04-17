package com.ispan.dogland.model.dto;

public class ActivityGalleryDto {
    private Integer galleryId;
    private String galleryImgUrl;
    private String galleryImgType;

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public String getGalleryImgUrl() {
        return galleryImgUrl;
    }

    public void setGalleryImgUrl(String galleryImgUrl) {
        this.galleryImgUrl = galleryImgUrl;
    }

    public String getGalleryImgType() {
        return galleryImgType;
    }

    public void setGalleryImgType(String galleryImgType) {
        this.galleryImgType = galleryImgType;
    }
}
