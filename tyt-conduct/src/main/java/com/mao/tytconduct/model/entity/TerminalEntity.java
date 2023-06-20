package com.mao.tytconduct.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tt_terminal")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TerminalEntity extends BaseEntity {

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
