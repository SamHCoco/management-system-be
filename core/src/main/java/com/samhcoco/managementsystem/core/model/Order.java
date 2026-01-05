package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.model.dto.OrderDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity(name = "order")
public class Order extends Auditable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_id")
    private long productId;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "user_id")
    private long userId;

    public OrderDto toDto() {
        return OrderDto.builder()
                       .id(id)
                       .productId(productId)
                       .userId(userId)
                       .quantity(quantity)
                       .build();
    }
}
