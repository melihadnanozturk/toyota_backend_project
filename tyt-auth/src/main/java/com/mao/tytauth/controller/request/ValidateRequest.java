package com.mao.tytauth.controller.request;

import com.mao.tytauth.model.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidateRequest {

    private String user;
    private String token;
    private Role role;
}
