package com.ispan.dogland.model.dao;

import com.ispan.dogland.model.entity.OrderCancel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderCancelRepository extends JpaRepository<OrderCancel,Integer> {
}
