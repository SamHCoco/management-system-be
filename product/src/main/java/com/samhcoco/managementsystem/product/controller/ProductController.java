package com.samhcoco.managementsystem.product.controller;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private static final String PRODUCT = "product";

    private final ProductService productService;

    @PreAuthorize("hasRole('admin')")
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
