package com.mao.tytmistake.model;

import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.service.impl.TestDataBuilder;

public class DefectEntityBuilder extends TestDataBuilder<DefectEntity> {

    public DefectEntityBuilder() {
        super(DefectEntity.class, true);
    }
}
