package com.samhcoco.managementsystem.core.model.dto;

import com.samhcoco.managementsystem.core.model.Product;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private long id;
    private String name;
    private BigDecimal price;

    public Product toProduct() {
        return Product.builder()
                      .id(id)
                      .name(name)
                      .price(price)
                      .build();
    }
}
