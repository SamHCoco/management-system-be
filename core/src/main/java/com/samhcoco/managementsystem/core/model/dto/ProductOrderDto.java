package com.samhcoco.managementsystem.core.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.samhcoco.managementsystem.core.model.ProductOrder;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
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
