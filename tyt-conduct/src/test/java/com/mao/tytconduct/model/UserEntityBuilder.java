package com.mao.tytconduct.model;

import com.mao.tytconduct.TestDataBuilder;
import com.mao.tytconduct.model.entity.UserEntity;

public class UserEntityBuilder extends TestDataBuilder<UserEntity> {

    public UserEntityBuilder(boolean isRelation) {
        super(UserEntity.class, isRelation);
    }

    public UserEntityBuilder withName(String name) {
        data.setName(name);
        return this;
    }

    public UserEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }

    public UserEntityBuilder withPassword(String password) {
        data.setPassword(password);
        return this;
    }
}
