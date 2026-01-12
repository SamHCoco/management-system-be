package com.samhcoco.managementsystem.product.model;

import com.samhcoco.managementsystem.core.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_inventory")
public class ProductInventory extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "stock")
    private int stock;

    @Column(name = "low_stock_threshold")
    private int lowStockThreshold;

    @Column(name = "low_stock_alerted")
    private boolean lowStockAlerted;

    @Column(name = "deleted")
    private boolean deleted;
}
