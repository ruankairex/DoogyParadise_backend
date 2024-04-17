package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.ProductGallery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductGalleryRepository extends JpaRepository<ProductGallery,Integer> {
    @Query("SELECT pg FROM ProductGallery pg JOIN pg.product p ON p.productId = :productId")
    List<ProductGallery> findByProductId(Integer productId);
}
