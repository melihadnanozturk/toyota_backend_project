package com.mao.tytmistake.model;

import com.mao.tytmistake.TestDataBuilder;
import com.mao.tytmistake.model.entity.DefectEntity;

public class DefectEntityBuilder extends TestDataBuilder<DefectEntity> {

    public DefectEntityBuilder() {
        super(DefectEntity.class, true);
    }

    public DefectEntityBuilder withId(Long id) {
        data.setId(id);
        return this;
    }

    public DefectEntityBuilder withImage(byte[] image) {
        data.setDefectImage(image);
        return this;
    }
}
