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

    private static final String SYSTEM = "system";

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "last_modified_at")
    private Instant lastModifiedAt;

    @Column(name = "last_modified_by")
    private String lastModifiedBy;

    @Column(name = "deleted")
    private boolean deleted;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null && lastModifiedAt == null) {
            final Instant now = Instant.now();
            createdAt = now;
            lastModifiedAt = now;
        }
        if (lastModifiedBy == null) {
            lastModifiedBy = SYSTEM;
        }
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = Instant.now();
        if (lastModifiedBy == null) {
            lastModifiedBy = SYSTEM;
        }
    }

}
