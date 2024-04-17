package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.ShoppingCart;
import com.ispan.dogland.model.entity.Users;
import com.ispan.dogland.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart,Integer> {

    public ShoppingCart findByUsersAndProduct(Users m , Product p);

    public List<ShoppingCart> findByUsers(Users m);

}
