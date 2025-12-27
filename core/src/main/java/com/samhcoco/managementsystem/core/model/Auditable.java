package com.samhcoco.managementsystem.core.model;

import jakarta.persistence.Column;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Auditable {

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "last_modified_at")
    private Instant lastModifiedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastModifiedAt = Instant.now();
    }

}
