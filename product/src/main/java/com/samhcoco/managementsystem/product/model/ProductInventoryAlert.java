package com.samhcoco.managementsystem.product.model;

import com.samhcoco.managementsystem.core.enums.MessageType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class ProductInventoryAlert {
    private long productId;
    private String productName;
    private int stock;
    private int lowStockThreshold;
    private MessageType messageType;
}
