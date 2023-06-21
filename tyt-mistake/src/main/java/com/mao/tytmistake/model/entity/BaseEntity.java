package com.mao.tytmistake.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Base entity class with common fields and functionality for entities.
 */
@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 3575817800743620899L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean")
    private Boolean isDeleted = false;

    private LocalDate createdAt;

    private String createdBy;

    private LocalDate updatedAt;

    private String updatedBy;

    /**
     * Executed before the entity is persisted to the database.
     * Sets the creation date to the current date.
     */
    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
    }

    /**
     * Executed before the entity is updated in the database.
     * Sets the update date to the current date.
     */
    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now();
    }
}
