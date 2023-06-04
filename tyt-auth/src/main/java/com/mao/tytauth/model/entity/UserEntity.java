package com.mao.tytauth.model.entity;

import com.mao.tytauth.model.entity.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Table(name = "tt_user")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @ElementCollection
    @Column(columnDefinition = "smallint[]")
    private List<Role> roles = new ArrayList<>();
}
