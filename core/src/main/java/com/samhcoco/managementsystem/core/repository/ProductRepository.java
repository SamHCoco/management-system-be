package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
}
