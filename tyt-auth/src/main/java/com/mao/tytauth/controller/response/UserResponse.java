package com.mao.tytauth.controller.response;

import com.mao.tytauth.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;

@Getter
@Setter
@Builder
public class UserResponse {

    private String name;
    private String password;
    private HashSet<Role> roles;
}
