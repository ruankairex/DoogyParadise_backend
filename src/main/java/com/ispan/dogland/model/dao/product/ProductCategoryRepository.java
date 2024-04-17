package com.ispan.dogland.model.dao.product;


import com.ispan.dogland.model.entity.product.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {
    ProductCategory findByCategoryId(Integer categoryId);

    ProductCategory findByCategoryName(String categoryName);
}
