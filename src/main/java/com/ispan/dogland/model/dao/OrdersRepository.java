package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.OrderDetail;
import com.ispan.dogland.model.entity.Orders;
import com.ispan.dogland.model.entity.tweet.Tweet;
import org.springframework.data.jpa.repository.JpaRepository;
import com.ispan.dogland.model.entity.Users;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders,Integer> {

    public List<Orders> findByUsers(Users m);

//    public Orders findByUsersAndOrdersId(Users m,Integer ordersId);

    @Query("SELECT t FROM Orders t LEFT JOIN FETCH t.users")
    List<Orders> findAllOrdersWithUsers();

    // 根据訂單ID查询對應的用户ID
    @Query("SELECT o.users.userId FROM Orders o WHERE o.orderId = :orderId")
    Integer findUserIdByOrderId(Integer orderId);

    // 根據用戶ID查詢訂單
    List<Orders> findByUsers_UserId(Integer userId);

    @Query("SELECT o FROM Orders o JOIN o.users u ON u.userId = :userId")
    List<Orders> findOrdersByUserId(Integer userId);

    Orders findByOrderId(Integer orderId);
}
