package com.mao.tytmistake.model;

import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class VehicleEntityBuilder extends TestDataBuilder<VehicleEntity> {
    public VehicleEntityBuilder() {
        super(VehicleEntity.class, true);
    }
}
