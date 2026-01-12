package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.product.model.Product;
import com.samhcoco.managementsystem.product.model.ProductInventory;

public interface ProductInventoryService {
    ProductInventory create(Product product, Integer stock);
}
