package com.samhcoco.managementsystem.core.repository;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findById(long id);

    boolean existsByIdInAndDeletedFalse(Set<Long> ids);

    @Query(value = """
                   SELECT p.id as id, p.name as name, p.price as price
                   FROM product p
                   WHERE p.deleted = false
       """,
           countQuery = "SELECT COUNT(*) FROM product p WHERE p.deleted = false",
           nativeQuery = true)
    Page<ProductDto> findAllByDeletedFalse(Pageable pageable);
}
