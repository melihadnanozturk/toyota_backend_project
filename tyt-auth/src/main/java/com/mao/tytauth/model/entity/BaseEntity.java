package com.mao.tytauth.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2575739952112205541L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "boolean")
    private Boolean isDeleted = false;

    private LocalDate createdAt;

    private String createdBy;

    private LocalDate updatedAt;

    private String updatedBy;


    @PrePersist
    public void prePersist() {
        createdAt = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = LocalDate.now();
    }
}
