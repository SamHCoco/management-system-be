package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.model.dto.ProductOrderDto;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class OrderPayment {
    private BigDecimal amount;
    private long userId;
    private UUID orderId;
    private List<ProductOrderDto> orders;
}
