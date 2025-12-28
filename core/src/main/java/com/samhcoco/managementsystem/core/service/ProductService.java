package com.samhcoco.managementsystem.core.service;

import com.samhcoco.managementsystem.core.model.Product;

public interface ProductService {
    Product create(Product product, Long quantity);
}
