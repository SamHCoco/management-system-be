package com.samhcoco.managementsystem.core.service.impl;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.ProductInventory;
import com.samhcoco.managementsystem.core.repository.ProductRepository;
import com.samhcoco.managementsystem.core.service.ProductInventoryService;
import com.samhcoco.managementsystem.core.service.ProductService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductInventoryService productInventoryService;

    @Override
    @Transactional
    public Product create(@NonNull Product product, @NonNull Long quantity) {
        final Product created = productRepository.save(product);
        final ProductInventory productInventory = productInventoryService.create(product, quantity);
        return product;
    }
}
