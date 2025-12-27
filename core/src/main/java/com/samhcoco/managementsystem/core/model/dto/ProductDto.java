package com.samhcoco.managementsystem.core.model.dto;

import com.samhcoco.managementsystem.core.model.Product;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ProductDto {
    private long id;
    private String name;

    public Product toProduct() {
        return Product.builder()
                      .id(id)
                      .name(name)
                      .build();
    }
}
