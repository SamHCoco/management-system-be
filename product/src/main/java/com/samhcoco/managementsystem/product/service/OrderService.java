package com.samhcoco.managementsystem.product.service;

import com.samhcoco.managementsystem.core.model.Order;
import com.samhcoco.managementsystem.product.model.dto.ProductOrdersDto;

import java.util.List;

public interface OrderService {
    /**
     * Creates {@link Order}s for the supplied {@link ProductOrdersDto}, for the given user ID.
     * @param productOrdersDto {@link ProductOrdersDto}.
     * @param userId User ID.
     * @return Created {@link Order}s.
     */
    List<Order> create(ProductOrdersDto productOrdersDto, long userId);
}
