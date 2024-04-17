package com.ispan.dogland.model.entity.product;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@Entity
@Table(name = "product_gallery_cloud")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "product")
public class ProductGalleryCloud {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cloud_id")
    private Integer cloudId;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", nullable = false)
    @JsonIgnore
    private Product product;

    @Column(name = "cloud_path", nullable = false)
    private String cloudPath;

    @Column(name = "cloud_public_id")
    private String cloudPublicId;

    ///////////////////////////////////

    public ProductGalleryCloud() {}

    public ProductGalleryCloud(String cloudPath) {
        this.cloudPath = cloudPath;
    }

    ///////////////////////////////////


    public Integer getCloudId() {
        return cloudId;
    }

    public void setCloudId(Integer cloudId) {
        this.cloudId = cloudId;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getCloudPath() {
        return cloudPath;
    }

    public void setCloudPath(String cloudPath) {
        this.cloudPath = cloudPath;
    }

    public String getCloudPublicId() {
        return cloudPublicId;
    }

    public void setCloudPublicId(String cloudPublicId) {
        this.cloudPublicId = cloudPublicId;
    }
}
