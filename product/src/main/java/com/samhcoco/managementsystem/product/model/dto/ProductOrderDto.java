package com.samhcoco.managementsystem.product.model.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOrderDto {
    private long productId;
    private int quantity;
}
