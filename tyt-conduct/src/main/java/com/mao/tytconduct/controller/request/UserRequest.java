package com.mao.tytconduct.controller.request;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRequest {

    private String name;
    private String password;
    private List<Role> roles;

    public static UserEntity mappedToEntity(UserRequest request) {
        return UserEntity.builder()
                .name(request.name)
                .password(request.password)
                .roles(request.getRoles())
                .build();
    }

}
