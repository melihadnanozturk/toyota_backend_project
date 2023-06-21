package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.model.entity.UserEntity;
import com.mao.tytconduct.model.enums.Role;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

/**
 * This class is used to response user information.
 * This class contains information field about to user.
 * The class uses the Lombok annotations @Getter,@Setter and @Builder to automatically generate getters, setters and a builders,@EqualsAndHashCode to equals process.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class UserResponse {

    private Long id;
    private String name;
    private String password;
    private HashSet<Role> roles;

    /**
     * Maps the UserEntity to the UserResponse
     *
     * @param userEntity UserEntity to be mapped
     * @return UserResponse holding information mapped from userEntity
     */
    public static UserResponse entityMappedToResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .password(userEntity.getPassword())
                .roles(userEntity.getRoles())
                .build();
    }
}
