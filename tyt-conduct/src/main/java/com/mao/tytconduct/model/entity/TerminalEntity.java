package com.mao.tytconduct.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Table(name = "tt_terminal")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerminalEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -5979363465218192211L;

    @Column(name = "term_name")
    private String name;

    @Column(name = "softDelete")
    private Boolean sofDelete;

    @Column(name = "pagination")
    private Boolean pagination;

    @Column(name = "term_ability_create")
    private Boolean abilityToCreate;

    @Column(name = "term_ability_update")
    private Boolean abilityToUpdate;

    @Column(name = "term_ability_delete")
    private Boolean abilityToDelete;

}
