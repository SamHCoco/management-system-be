package com.samhcoco.managementsystem.product.repository;

import com.samhcoco.managementsystem.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);
    boolean existsByIdInAndDeletedFalse(Set<Long> ids);
}
