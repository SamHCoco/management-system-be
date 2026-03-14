package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import com.samhcoco.managementsystem.product.model.ProductInventory;
import org.springframework.data.domain.Page;

public interface ProductService {

    /**
     * Creates a new {@link Product} and corresponding {@link ProductInventory}
     * for the given quantity that specifies the stock for the product.
     * @param product The {@link Product}.
     * @param stockQuantity The stock quantity of the {@link Product}.
     * @return Created {@link Product}.
     */
    Product create(Product product, Integer stockQuantity);

    /**
     * Lists all {@link Product}s.
     * @param appPage {@link AppPage}.
     * @return {@link Page} of {@link ProductDto}.
     */
    Page<ProductDto> listAllProducts(AppPage appPage);
}
