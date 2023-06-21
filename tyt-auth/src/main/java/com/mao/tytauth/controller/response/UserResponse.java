package com.mao.tytauth.controller.response;

import com.mao.tytauth.model.Role;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;

/**
 * This class is POJO class that contains the user's name, surname and information.
 * The class uses the Lombok annotations @Data and @Builder to automatically generate getters, setters, and a builder method.
 */
@Data
@Builder
public class UserResponse {

    private String name;
    private String password;
    private HashSet<Role> roles;
}
