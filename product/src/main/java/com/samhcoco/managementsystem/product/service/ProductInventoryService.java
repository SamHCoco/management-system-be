package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductOrderDto;
import com.samhcoco.managementsystem.product.model.ProductInventory;

import java.util.List;

public interface ProductInventoryService {
    /**
     * Creates a new {@link ProductInventory} for the given {@link Product}.
     * @param product The {@link Product} for the {@link ProductInventory} that will be created.
     * @param stock Stock quantity of the product.
     * @return The persisted {@link ProductInventory}.
     */
    ProductInventory create(Product product, Integer stock);

    /**
     * Updates the stock count for {@link ProductInventory} for the given {@link ProductOrderDto}s.
     * @param productOrderDtos {@link ProductOrderDto}s.
     * @return Updated {@link ProductInventory}.
     */
    List<ProductInventory> subtractInventoryStock(List<ProductOrderDto> productOrderDtos);
}
