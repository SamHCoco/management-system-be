package com.samhcoco.managementsystem.order.model;

import com.samhcoco.managementsystem.core.model.Auditable;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "order")
public class Order extends Auditable {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private long userId;

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }
}