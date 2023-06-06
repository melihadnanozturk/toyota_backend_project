package com.mao.tytauth.controller.request;

import com.mao.tytauth.model.entity.UserEntity;
import com.mao.tytauth.model.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
