package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.core.model.ProductOrder;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDtoList;

import java.util.List;

public interface ProductOrderService {
    /**
     * Creates {@link ProductOrder}s for the supplied {@link ProductOrderDtoList}, for the given user ID.
     * @param productOrderDtoList {@link ProductOrderDtoList}.
     * @param userId User ID.
     * @return Created {@link ProductOrder}s.
     */
    List<ProductOrder> create(ProductOrderDtoList productOrderDtoList, long userId);
}
