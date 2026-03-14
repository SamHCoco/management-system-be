package com.samhcoco.managementsystem.product.controller;

import com.samhcoco.managementsystem.core.model.AppPage;
import com.samhcoco.managementsystem.core.model.Product;
import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import com.samhcoco.managementsystem.core.utils.ApiVersion;
import com.samhcoco.managementsystem.product.service.ProductService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ProductController {
    private static final String PRODUCTS = "products";

    private final ProductService productService;

    @PreAuthorize("hasRole('admin')")
    @SecurityRequirement(name = "bearerAuth")
    @PostMapping(ApiVersion.VERSION_1 + "/" + PRODUCTS)
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

    @GetMapping(ApiVersion.VERSION_1 + "/" + PRODUCTS)
    public ResponseEntity<Object> listAllProduct(@RequestParam(required = false) Integer page,
                                                 @RequestParam(required = false) Integer size,
                                                 @RequestParam(required = false) String sort,
                                                 @RequestParam(required = false) String sortDirection) {
        AppPage appPage = new AppPage();
        if (nonNull(page)) {
            appPage.setPage(page);
        }
        if (nonNull(size)) {
            appPage.setSize(size);
        }
        if (!isBlank(sort)) {
            appPage.setSort(sort);
        }
        if (!isBlank(sortDirection)) {
            appPage.setSortDirection(sortDirection);
        }

        Page<ProductDto> pageResult = productService.listAllProducts(appPage);
        return ResponseEntity.ok(pageResult);
    }



}
