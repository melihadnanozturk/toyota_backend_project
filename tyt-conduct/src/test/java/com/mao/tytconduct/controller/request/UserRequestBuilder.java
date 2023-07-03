package com.mao.tytconduct.controller.request;

import com.mao.tytconduct.TestDataBuilder;

public class UserRequestBuilder extends TestDataBuilder<UserRequest> {

    public UserRequestBuilder() {
        super(UserRequest.class, false);
    }

    public UserRequestBuilder withName(String name) {
        data.setName(name);
        return this;
    }
}
