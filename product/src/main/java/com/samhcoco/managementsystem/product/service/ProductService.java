package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.product.model.Product;
import com.samhcoco.managementsystem.product.model.ProductInventory;

public interface ProductService {

    /**
     * Creates a new {@link Product} and corresponding {@link ProductInventory}
     * for the given quantity that specifies the stock for the product.
     * @param product The {@link Product}.
     * @param stockQuantity The stock quantity of the {@link Product}.
     * @return Created {@link Product}.
     */
    Product create(Product product, Integer stockQuantity);
}
