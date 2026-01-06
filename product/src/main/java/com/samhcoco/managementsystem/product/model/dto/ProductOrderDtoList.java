package com.samhcoco.managementsystem.product.model.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductOrderDtoList {
    private List<ProductOrderDto> orders;
}
