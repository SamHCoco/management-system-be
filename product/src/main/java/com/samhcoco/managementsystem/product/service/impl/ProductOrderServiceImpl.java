package com.samhcoco.managementsystem.product.service.impl;

import com.samhcoco.managementsystem.core.enums.MessageType;
import com.samhcoco.managementsystem.core.exception.OutOfStockException;
import com.samhcoco.managementsystem.core.model.ProductOrder;
import com.samhcoco.managementsystem.core.model.ProductInventory;
import com.samhcoco.managementsystem.core.model.ProductInventoryAlert;
import com.samhcoco.managementsystem.product.repository.OrderRepository;
import com.samhcoco.managementsystem.product.repository.ProductInventoryRepository;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDtoList;
import com.samhcoco.managementsystem.product.service.ProductOrderService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.samhcoco.managementsystem.core.enums.OrderStatus.PENDING;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductOrderServiceImpl implements ProductOrderService {

    private final OrderRepository orderRepository;
    private final ProductInventoryRepository productInventoryRepository;

    @Override
    @Transactional
    public List<ProductOrder> create(@NonNull ProductOrderDtoList productOrderDtoList, long userId) {
        final Map<Long, Integer> productIdsAndQuantities = new HashMap<>();

        productOrderDtoList.getOrders().forEach(o -> {
            productIdsAndQuantities.put(o.getProductId(), o.getQuantity());
        });

        final List<ProductInventory> inventory = productInventoryRepository.findAllByProductIdInAndDeletedFalse(productIdsAndQuantities.keySet());

        final List<ProductOrder> orders = new ArrayList<>();
        final List<ProductInventoryAlert> alerts = new ArrayList<>();

        inventory.forEach(i -> {
            if (i.getStock() == 0) {
                throw new OutOfStockException("Out Of Stock",
                                       Map.of("product", String.format("Failed to complete order: product with ID '%s' out of stock", i.getProductId())));
            }

            i.setStock(i.getStock() - 1);

            if (i.getStock() <= i.getLowStockThreshold() && i.isLowStockAlerted() == false) {
                alerts.add(ProductInventoryAlert.builder()
                                                .productId(i.getProductId())
                                                .stock(i.getStock())
                                                .lowStockThreshold(i.getLowStockThreshold())
                                                .messageType(MessageType.PRODUCT_INVENTORY_ALERT)
                                                .build());
                i.setLowStockAlerted(true);
            }

            orders.add(ProductOrder.builder()
                            .productId(i.getProductId())
                            .quantity(productIdsAndQuantities.get(i.getProductId()))
                            .userId(userId)
                            .status(PENDING.name())
                            .build());
        });

        productInventoryRepository.saveAll(inventory);
        // todo - send low stock inventory message
        return orderRepository.saveAll(orders);
    }
}
