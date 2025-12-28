package com.samhcoco.managementsystem.core.model;

import com.samhcoco.managementsystem.core.model.dto.ProductDto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "deleted")
    private boolean deleted;

    public ProductDto toDto() {
        return ProductDto.builder()
                         .id(id)
                         .name(name)
                         .price(price)
                         .build();
    }
}
