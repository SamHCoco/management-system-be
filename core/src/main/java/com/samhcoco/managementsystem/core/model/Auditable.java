package com.samhcoco.managementsystem.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.*;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class Auditable {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "last_modified_at")
    private Instant lastModifiedAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null && lastModifiedAt == null) {
            final Instant now = Instant.now();
            createdAt = now;
            lastModifiedAt = now;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = Instant.now();
    }

}
