package com.mao.tytmistake.model;

import com.mao.tytmistake.model.entity.LocationEntity;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class LocationEntityBuilder extends TestDataBuilder<LocationEntity> {

    public LocationEntityBuilder() {
        super(LocationEntity.class, true);
    }

    public LocationEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }
}
