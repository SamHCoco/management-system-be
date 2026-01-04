package com.samhcoco.managementsystem.product.controller;

import com.samhcoco.managementsystem.core.exception.InvalidInputApiException;
import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import com.samhcoco.managementsystem.core.service.ProductService;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.product.model.dto.ProductOrdersDto;
import com.samhcoco.managementsystem.product.service.OrderService;
import com.samhcoco.managementsystem.product.service.impl.ProductOrdersDtoEntityValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private static final String PRODUCT = "product";

    private final ProductService productService;
    private final OrderService orderService;
    private final ProductOrdersDtoEntityValidator productOrdersDtoEntityValidator;

    @PostMapping(ApiVersion.VERSION_1 + "/" + PRODUCT + "/orders")
    public ResponseEntity<Object> orderProduct(@RequestBody ProductOrdersDto productOrdersDto) {
        final Map<String, String> failureReasons = productOrdersDtoEntityValidator.validateCreate(productOrdersDto);

        if (!failureReasons.isEmpty()) {
            log.error("Failed to create Product Order for {}: {}", productOrdersDto, failureReasons);
            throw new InvalidInputApiException(BAD_REQUEST.name(), failureReasons);
        }

        // todo - complete
        return null;

    }

    @PostMapping(ApiVersion.VERSION_1 + "/" + PRODUCT)
    public ResponseEntity<Object> createProduct(@RequestBody ProductDto productDto,
                                                @RequestParam int stockQuantity) {
        try {
            // todo - extend AbstractEntityValidator to create validation service for product
            final Product created = productService.create(productDto.toProduct(), stockQuantity);

            return ResponseEntity.status(CREATED)
                                 .body(created.toDto());
        } catch(Exception e) {
            log.error("Failed to create {}: {}", productDto, e.getMessage());

            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                                 .body(e.getMessage());
        }
    }

}
