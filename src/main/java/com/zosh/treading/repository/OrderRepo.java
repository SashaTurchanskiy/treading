package com.zosh.treading.repository;

import com.zosh.treading.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Order findByUserId(long userId);

}
