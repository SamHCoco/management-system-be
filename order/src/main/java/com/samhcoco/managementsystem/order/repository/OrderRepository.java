package com.samhcoco.managementsystem.order.repository;

import com.samhcoco.managementsystem.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findById(long id);
    List<Order> findAllByUserId(long userId);
}
