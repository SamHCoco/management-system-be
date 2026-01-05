package com.samhcoco.managementsystem.core.model.dto;

import com.samhcoco.managementsystem.core.model.ProductOrder;
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
    private long quantity;

    public ProductOrder toProductOrder() {
        return ProductOrder.builder()
                    .id(id)
                    .productId(productId)
                    .quantity(quantity)
                    .build();
    }
}
