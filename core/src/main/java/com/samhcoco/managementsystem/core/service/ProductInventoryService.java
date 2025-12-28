package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.ProductInventory;

public interface ProductInventoryService {
    ProductInventory create(Product product, Long quantity);
}
