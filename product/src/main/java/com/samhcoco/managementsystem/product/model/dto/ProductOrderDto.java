package com.samhcoco.managementsystem.product.model.dto;

import com.samhcoco.managementsystem.product.model.ProductOrder;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class ProductOrderDto {
    private long id;
    private long productId;
    private short quantity;

    public ProductOrder toProductOrder() {
        return ProductOrder.builder()
                    .id(id)
                    .productId(productId)
                    .quantity(quantity)
                    .build();
    }
}
