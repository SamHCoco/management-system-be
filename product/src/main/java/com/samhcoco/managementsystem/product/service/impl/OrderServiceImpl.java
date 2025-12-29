package com.samhcoco.managementsystem.product.service.impl;

import com.samhcoco.managementsystem.core.enums.MessageType;
import com.samhcoco.managementsystem.core.exception.OutOfStockException;
import com.samhcoco.managementsystem.core.model.Order;
import com.samhcoco.managementsystem.core.model.ProductInventory;
import com.samhcoco.managementsystem.core.model.ProductInventoryAlert;
import com.samhcoco.managementsystem.core.repository.OrderRepository;
import com.samhcoco.managementsystem.core.repository.ProductInventoryRepository;
import com.samhcoco.managementsystem.product.model.dto.ProductOrdersDto;
import com.samhcoco.managementsystem.product.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductInventoryRepository productInventoryRepository;

    @Override
    @Transactional
    public List<Order> create(@NonNull ProductOrdersDto productOrdersDto, long userId) {
        final Map<Long, Integer> productIdsAndQuantities = new HashMap<>();

        productOrdersDto.getOrders().forEach(o -> {
            productIdsAndQuantities.put(o.getProductId(), o.getQuantity());
        });

        final List<ProductInventory> productInventory = productInventoryRepository.findAllByProductIdInAndDeletedFalse(productIdsAndQuantities.keySet());

        final List<Order> orders = new ArrayList<>();
        final List<ProductInventoryAlert> alerts = new ArrayList<>();

        productInventory.forEach(p -> {
            if (p.getStock() < 1) {
                throw new OutOfStockException("Out Of Stock",
                                       Map.of("product", String.format("Failed to complete order: product with ID '%s' out of stock", p.getProductId())));
            }

            p.setStock(p.getStock() - 1);

            if (p.getStock() <= p.getLowStockThreshold() && p.isLowStockAlerted() == false) {
                alerts.add(ProductInventoryAlert.builder()
                                                .productId(p.getProductId())
                                                .stock(p.getStock())
                                                .lowStockThreshold(p.getLowStockThreshold())
                                                .messageType(MessageType.PRODUCT_INVENTORY_ALERT)
                                                .build());
                p.setLowStockAlerted(true);
            }

            orders.add(Order.builder()
                            .productId(p.getProductId())
                            .quantity(productIdsAndQuantities.get(p.getProductId()))
                            .userId(userId)
                            .build());
        });

        productInventoryRepository.saveAll(productInventory);
        // todo - send low stock inventory message
        return orderRepository.saveAll(orders);
    }
}
