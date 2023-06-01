package com.mao.tytconduct.controller.request;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    @NotBlank
    private String name;
    @NotBlank
    private String password;
    @NotNull
    private List<Role> roles;

    public static UserEntity mappedToEntity(UserRequest request) {
        return UserEntity.builder()
                .name(request.name)
                .password(request.password)
                .roles(request.getRoles())
                .build();
    }

}
