package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail,Integer> {
    @Query("SELECT od FROM OrderDetail od JOIN od.orders o ON o.orderId = :orderId")
    List<OrderDetail> findByOrderId(Integer orderId);

    //把傳入的productId的訂單數量加總(沒用到)
    @Query("SELECT SUM(od.quantity) FROM OrderDetail od WHERE od.productId = :productId")
    Integer sumQuantityByProductId(Integer productId);

    //把所有商品數量加總回傳(沒用到)
    @Query("SELECT SUM(od.quantity) FROM OrderDetail od")
    Integer sumTotalQuantity();

    @Query("SELECT od.productId, SUM(od.quantity) FROM OrderDetail od GROUP BY od.productId")
    List<Object[]> sumQuantityByProductId();

}
