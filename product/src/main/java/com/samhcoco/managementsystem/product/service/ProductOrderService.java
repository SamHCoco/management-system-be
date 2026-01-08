package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.core.model.ProductOrder;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderListDto;

import java.util.List;

public interface ProductOrderService {
    /**
     * Creates {@link ProductOrder}s for the supplied {@link ProductOrderListDto}, for the given user ID.
     * @param productOrderListDto {@link ProductOrderListDto}.
     * @param userId User ID.
     * @return Created {@link ProductOrder}s.
     */
    List<ProductOrder> create(ProductOrderListDto productOrderListDto, long userId);
}
