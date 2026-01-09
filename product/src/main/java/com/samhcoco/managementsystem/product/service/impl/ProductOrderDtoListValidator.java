package com.samhcoco.managementsystem.product.service.impl;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.repository.ProductRepository;
import com.samhcoco.managementsystem.core.service.CreateEntityValidator;
import com.samhcoco.managementsystem.core.service.JpaRepositoryService;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDto;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDtoList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Getter
@Setter
@RequiredArgsConstructor
@Service
public class ProductOrderDtoListValidator implements CreateEntityValidator<ProductOrderDtoList, Long> {

    private final JpaRepositoryService repositoryService;

    @Override
    public Map<String, String> validateCreate(ProductOrderDtoList entity) {
        final Map<String, String> failureReasons = new HashMap<>();

        if (isNull(entity)) {
            failureReasons.put("Entity", "Entity cannot be null");
            return failureReasons;
        }

        final List<ProductOrderDto> orders = entity.getOrders();
        if (isNull(orders)) {
            failureReasons.put("ProductOrderDto", "ProductOrderDto cannot be null");
            return failureReasons;
        }

        final Set<Long> productIds = orders.stream()
                                           .map(ProductOrderDto::getProductId)
                                           .filter(productId -> productId != 0)
                                           .collect(Collectors.toSet());

        if (productIds.isEmpty()) {
            failureReasons.put("productId", "Product IDs invalid");
            return failureReasons;
        }

        if (productIds.size() != orders.size()) {
            failureReasons.put("productId", "Some invalid Product IDs were supplied");
            return failureReasons;
        }

        final ProductRepository productRepository = repositoryService.getRepository(Product.class);
        if (isNull(productRepository)) {
            failureReasons.put("productRepository", String.format("Internal Error - failed to find JpaRepository for Class: '%s'", Product.class));
            return failureReasons;
        }

        if (!productRepository.existsByIdInAndDeletedFalse(productIds)) {
            failureReasons.put("productId", "One or more supplied Product IDs do not exist");
            return failureReasons;
        }

        return failureReasons;
    }
}
