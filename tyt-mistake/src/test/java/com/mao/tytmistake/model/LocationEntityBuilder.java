package com.mao.tytmistake.model;

import com.mao.tytmistake.TestDataBuilder;
import com.mao.tytmistake.model.entity.LocationEntity;

public class LocationEntityBuilder extends TestDataBuilder<LocationEntity> {

    public LocationEntityBuilder() {
        super(LocationEntity.class, true);
    }

    public LocationEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }
}
