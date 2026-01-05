package com.samhcoco.managementsystem.core.model.dto;

import com.samhcoco.managementsystem.core.model.Order;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@EqualsAndHashCode
public class OrderDto {
    private long id;
    private long productId;
    private long quantity;
    private long userId;

    public Order toOrder() {
        return Order.builder()
                    .id(id)
                    .productId(productId)
                    .quantity(quantity)
                    .userId(userId)
                    .build();
    }
}
