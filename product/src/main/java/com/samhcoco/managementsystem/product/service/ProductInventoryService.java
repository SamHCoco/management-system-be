package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.ProductInventory;

public interface ProductInventoryService {
    ProductInventory create(Product product, Integer stock);
}
