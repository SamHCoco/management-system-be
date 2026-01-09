package com.samhcoco.managementsystem.product.controller;

import com.samhcoco.managementsystem.core.exception.InvalidInputApiException;
import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.ProductOrder;
import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import com.samhcoco.managementsystem.core.model.dto.ProductOrderDto;
import com.samhcoco.managementsystem.core.service.AuthService;
import com.samhcoco.managementsystem.core.service.ProductService;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDtoList;
import com.samhcoco.managementsystem.product.service.ProductOrderService;
import com.samhcoco.managementsystem.product.service.impl.ProductOrderDtoListValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private static final String PRODUCT = "product";

    private final ProductService productService;
    private final ProductOrderService productOrderService;
    private final AuthService authService;
    private final ProductOrderDtoListValidator productOrdersDtoValidator;

    @PreAuthorize("hasRole('user')")
    @PostMapping(ApiVersion.VERSION_1 + "/" + PRODUCT + "/orders")
    public ResponseEntity<List<ProductOrderDto>> orderProduct(@RequestBody ProductOrderDtoList productOrderDtoList) {
        final long userId = authService.verifyUserAuthorised();

        final Map<String, String> failureReasons = productOrdersDtoValidator.validateCreate(productOrderDtoList);
        if (!failureReasons.isEmpty()) {
            log.error("Failed to create Product Order for {}: {}", productOrderDtoList, failureReasons);
            throw new InvalidInputApiException(BAD_REQUEST.name(), failureReasons);
        }
        
        final List<ProductOrderDto> orderDtos = productOrderService.create(productOrderDtoList, userId)
                                                                                .stream()
                                                                                .map(ProductOrder::toProductOrderDto)
                                                                                .toList();
        return ResponseEntity.status(CREATED).body(orderDtos);
    }

    // todo - restrict to admin role
    @PostMapping(ApiVersion.VERSION_1 + "/" + PRODUCT)
    public ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto,
                                                @RequestParam int stockQuantity) {
        try {
            // todo - extend CreateEntityValidator to create validation service for product
            final Product created = productService.create(productDto.toProduct(), stockQuantity);

            return ResponseEntity.status(CREATED).body(created.toDto());
        } catch(Exception e) {
            log.error("Failed to create {}: {}", productDto, e.getMessage());

            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
