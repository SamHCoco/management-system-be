package com.samhcoco.managementsystem.product.model;

import com.samhcoco.managementsystem.core.model.Auditable;
import com.samhcoco.managementsystem.product.model.dto.ProductOrderDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity(name = "product_order")
public class ProductOrder extends Auditable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private short quantity;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "status")
    private String status;

    public ProductOrderDto toProductOrderDto() {
        return ProductOrderDto.builder()
                              .id(id)
                              .productId(productId)
                              .quantity(quantity)
                              .build();
    }
}
