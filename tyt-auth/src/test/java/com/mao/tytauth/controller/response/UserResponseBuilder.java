package com.mao.tytauth.controller.response;

import com.mao.tytauth.TestDataBuilder;
import com.mao.tytauth.model.Role;

public class UserResponseBuilder extends TestDataBuilder<UserResponse> {
    public UserResponseBuilder() {
        super(UserResponse.class, true);
    }

    public UserResponseBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public UserResponseBuilder withPassword(String password) {
        data.setPassword(password);
        return this;
    }

    public UserResponseBuilder withRole(Role role) {
        data.getRoles().add(role);
        return this;
    }
}
