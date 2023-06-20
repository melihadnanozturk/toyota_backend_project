package com.mao.tytmistake.model;

import com.mao.tytmistake.TestDataBuilder;
import com.mao.tytmistake.model.entity.VehicleEntity;

public class VehicleEntityBuilder extends TestDataBuilder<VehicleEntity> {
    public VehicleEntityBuilder() {
        super(VehicleEntity.class, true);
    }

    public VehicleEntityBuilder withChassisNumber(String chassisNumber) {
        data.setChassisNumber(chassisNumber);
        return this;
    }

    public VehicleEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }
}
