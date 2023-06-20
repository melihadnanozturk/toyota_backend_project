package com.mao.tytmistake.model;

import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class DefectEntityBuilder extends TestDataBuilder<DefectEntity> {

    public DefectEntityBuilder() {
        super(DefectEntity.class, true);
    }

    public DefectEntityBuilder withVehicle(VehicleEntity vehicleEntity) {
        data.setVehicle(vehicleEntity);
        return this;
    }

    public DefectEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }

    public DefectEntityBuilder withImage(String image) {
        data.setDefectImage(image);
        return this;
    }
}
