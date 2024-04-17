package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.Product;
import com.ispan.dogland.model.entity.product.ProductCategory;
import jdk.jfr.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("SELECT p FROM Product p WHERE p.category.categoryId = :categoryId")
    Page<Product> findByCategoryId(Pageable pageable,Integer categoryId);

    //關鍵字搜尋功能
    Page<Product> findAllByProductNameContaining(Pageable pageable,String keyword);

    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    List<Product> findByProductId(Integer productId);

    @Query("SELECT p FROM Product p WHERE p.productId = :productId")
    Product findSingleByProductId(Integer productId);
}
