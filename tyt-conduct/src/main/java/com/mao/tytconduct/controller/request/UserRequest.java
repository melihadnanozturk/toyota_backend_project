package com.mao.tytconduct.controller.request;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

/**
 * This class is used to query user information
 * This class is POJO class that contains the user's name, password and roles.
 * The class uses the Lombok annotations @Getter, @Setter and @Builder to automatically generate getters, setters, and a builder method.
 */
@Getter
@Setter
public class UserRequest {

    @NotBlank(message = "Name can not be blank")
    private String name;
    @NotBlank(message = "Password can not be blank")
    private String password;
    @NotNull(message = "Roles can not null")
    private HashSet<Role> roles;

    /**
     * Maps the UserRequest to the UserEntity
     *
     * @param request Request to be mapped
     * @return UserEntity holding information mapped from request
     */
    public static UserEntity mappedToEntity(UserRequest request) {
        return UserEntity.builder()
                .name(request.name)
                .password(request.password)
                .roles(request.getRoles())
                .build();
    }

}
