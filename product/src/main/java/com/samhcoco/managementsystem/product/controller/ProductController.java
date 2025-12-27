package com.samhcoco.managementsystem.product.controller;

import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    private static final String V1 = "api/1";
    private static final String PRODUCT = "product";

    private final ProductService productService;

    @PostMapping(V1 + "/" + PRODUCT)
    public ResponseEntity<?> createProduct(@RequestParam long quantity,
                                                    @RequestBody Product product) {
        try {
            // todo - add validation
            final Product created = productService.create(product, quantity);

            return ResponseEntity.status(CREATED)
                                 .body(created.toDto());
        } catch(Exception e) {
            log.error("Failed to create {}: {}", product, e.getMessage());
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
