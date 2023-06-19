package com.mao.tytconduct.controller.response;

import com.mao.tytconduct.TestDataBuilder;

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
}
