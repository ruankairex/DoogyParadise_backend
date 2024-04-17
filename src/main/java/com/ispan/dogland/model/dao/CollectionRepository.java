package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.Collection;
import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.product.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {

    @Transactional
    void deleteByUsersAndProduct(Users user, Product product);

    public Collection findByUsersAndProduct(Users user, Product product);

//    public List<Collection> findByUsers(Users m);

    Page<Collection> findByUsers(Users m, Pageable pageable);

}
