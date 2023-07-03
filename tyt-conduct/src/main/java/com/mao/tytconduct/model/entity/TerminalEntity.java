package com.mao.tytconduct.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;


/**
 * Entity class representing a terminal in the system.
 */
@Table(name = "tt_terminal")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TerminalEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -2993155797185341905L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
