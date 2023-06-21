package com.mao.tytconduct.model.entity;

import com.mao.tytconduct.model.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;

/**
 * Entity class representing a user in the system.
 */
@Table(name = "tt_user")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 2412919361061342382L;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(columnDefinition = "smallint[]")
    private HashSet<Role> roles;
}
