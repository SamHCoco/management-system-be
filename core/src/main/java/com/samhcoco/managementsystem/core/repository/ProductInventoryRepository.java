package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.ProductInventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventory, Long> {
    ProductInventory findById(long id);
    boolean existsByProductIdAndDeletedFalse(long ProductId);
    List<ProductInventory> findAllByProductIdInAndDeletedFalse(Set<Long> productIds);
}
