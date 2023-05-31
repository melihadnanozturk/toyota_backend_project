package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserResponse {

    private String name;
    private String password;
    private List<Role> roles;

    public static UserResponse entityMappedToResponse(UserEntity entity) {
        return UserResponse.builder()
                .name(entity.getName())
                .password(entity.getPassword())
                .roles(entity.getRoles())
                .build();
    }
}
