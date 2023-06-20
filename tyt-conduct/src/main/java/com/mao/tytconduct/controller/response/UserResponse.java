package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserResponse {

    private Long id;
    private String name;
    private String password;
    private HashSet<Role> roles;

    public static UserResponse entityMappedToResponse(UserEntity entity) {
        return UserResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .build();
    }
}
