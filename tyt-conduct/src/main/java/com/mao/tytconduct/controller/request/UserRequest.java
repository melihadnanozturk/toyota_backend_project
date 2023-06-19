package com.mao.tytconduct.controller.request;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Password can not be blank")
    private String password;
    @NotNull(message = "Roles can not null")
    private HashSet<Role> roles;

    public static UserEntity mappedToEntity(UserRequest request) {
        return UserEntity.builder()
                .name(request.name)
                .password(request.password)
                .roles(request.getRoles())
                .build();
    }

}
