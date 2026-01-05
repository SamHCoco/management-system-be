package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.ProductOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<ProductOrder, Long> {

}
