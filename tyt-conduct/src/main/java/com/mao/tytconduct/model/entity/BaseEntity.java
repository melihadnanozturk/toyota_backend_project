package com.mao.tytconduct.model.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2182574219753585901L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean")
    private Boolean isDeleted = false;

    private LocalDate createdAt;

    private String createdBy;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
    }
}
