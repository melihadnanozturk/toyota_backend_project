package com.mao.tytauth.controller.response;

import com.mao.tytauth.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

/**
 * This class is used to response user information
 * This class is POJO class that contains the user's name, password and roles.
 * The class uses the Lombok annotations @Getter, @Setter and @Builder to automatically generate getters, setters, and a builder method.
 */
@Getter
@Setter
@Builder
public class UserResponse {

    private String name;
    private String password;
    private HashSet<Role> roles;
}
