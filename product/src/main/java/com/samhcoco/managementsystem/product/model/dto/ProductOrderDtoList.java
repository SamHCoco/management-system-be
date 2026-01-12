package com.samhcoco.managementsystem.product.model.dto;

import com.samhcoco.managementsystem.core.model.dto.ProductOrderDto;
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
