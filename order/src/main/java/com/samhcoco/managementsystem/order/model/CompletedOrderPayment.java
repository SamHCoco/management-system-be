package com.samhcoco.managementsystem.order.model;

import com.samhcoco.managementsystem.core.model.ProductOrder;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class CompletedOrderPayment {
    private Order orderDetails;
    private List<ProductOrder> orders;
}
