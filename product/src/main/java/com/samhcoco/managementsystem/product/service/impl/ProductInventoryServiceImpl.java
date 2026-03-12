package com.samhcoco.managementsystem.product.service.impl;

import com.samhcoco.managementsystem.core.model.OrderPayment;
import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductOrderDto;
import com.samhcoco.managementsystem.product.model.ProductInventory;
import com.samhcoco.managementsystem.product.repository.ProductInventoryRepository;
import com.samhcoco.managementsystem.product.service.ProductInventoryService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInventoryServiceImpl implements ProductInventoryService {

    private final ProductInventoryRepository productInventoryRepository;

    @Override
    public ProductInventory create(@NonNull Product product, @NonNull Integer stock) {
        if (productInventoryRepository.existsByProductIdAndDeletedFalse(product.getId())) {
            final String error = String.format("Failed to create ProductInventory for %s: " +
                                 "ProductInventory with Product ID '%s' already exists.", product, product.getId());
            log.error(error);
            throw new IllegalArgumentException(error);
        }

        val productInventory = ProductInventory.builder()
                                               .productId(product.getId())
                                               .stock(stock)
                                               .build();

        return productInventoryRepository.save(productInventory);
    }

    @KafkaListener(topics = "order-payments", groupId = "order-payments-group")
    private void consumeOrderPayment(OrderPayment orderPayment) {
        log.info("Received 'order-payment' record: '{}'", orderPayment);
        log.info("Attempting to update ProductInventory stock using Order Payment information");
        subtractInventoryStock(orderPayment.getOrders());
    }

    @Override
    public List<ProductInventory> subtractInventoryStock(@NonNull List<ProductOrderDto> productOrders) {
        Map<Long, Short> orderProductIdsAndQuantity = productOrders.stream()
                                                            .collect(Collectors.toMap(
                                                                    ProductOrderDto::getProductId,
                                                                    ProductOrderDto::getQuantity
                                                            ));

        List<ProductInventory> inventory = productInventoryRepository.findAllByProductIdInAndDeletedFalse(orderProductIdsAndQuantity.keySet());

        inventory.forEach(i -> {
            i.setStock(i.getStock() - orderProductIdsAndQuantity.get(i.getProductId()));
        });

        return productInventoryRepository.saveAll(inventory);
    }
}
