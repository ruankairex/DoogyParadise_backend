package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.ProductGalleryCloud;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductGalleryCloudRepository extends JpaRepository<ProductGalleryCloud,Integer> {
    ProductGalleryCloud findByCloudId(Integer imgId);
}
