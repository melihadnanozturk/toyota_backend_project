package com.mao.tytconduct.controller;

import com.mao.tytconduct.TestDataBuilder;
import com.mao.tytconduct.controller.request.UserRequest;

public class UserRequestBuilder extends TestDataBuilder<UserRequest> {

    public UserRequestBuilder() {
        super(UserRequest.class, false);
    }

    public UserRequestBuilder withName(String name) {
        data.setName(name);
        return this;
    }
}
