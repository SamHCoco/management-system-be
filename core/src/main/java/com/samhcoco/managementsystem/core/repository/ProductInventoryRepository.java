package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
    ProductInventory findById(long id);
    ProductInventory findByProductIdAndDeletedFalse(long productId);
}
