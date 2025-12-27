package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.ProductInventory;
import com.samhcoco.managementsystem.core.repository.ProductInventoryRepository;
import com.samhcoco.managementsystem.core.service.ProductInventoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import static java.util.Objects.nonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    @Override
    public ProductInventory create(@NonNull Product product, @NonNull Long quantity) {
        final ProductInventory existingInventory = productInventoryRepository.findByProductIdAndDeletedFalse(product.getId());
        if (nonNull(existingInventory)) {
            final String error = String.format("Failed to create ProductInventory for %s: " +
                                 "ProductInventory with Product ID '%s' already exists.", product, product.getId());
            log.error(error);
            throw new IllegalArgumentException(error);
        }

        val productInventory = ProductInventory.builder()
                                               .productId(product.getId())
                                               .quantity(quantity)
                                               .build();

        return productInventoryRepository.save(productInventory);
    }
}
