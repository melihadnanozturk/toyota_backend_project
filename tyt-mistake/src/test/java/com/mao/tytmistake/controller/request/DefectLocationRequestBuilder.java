package com.mao.tytmistake.controller.request;

import com.mao.tytmistake.TestDataBuilder;

public class DefectLocationRequestBuilder extends TestDataBuilder<DefectLocationRequest> {

    public DefectLocationRequestBuilder() {
        super(DefectLocationRequest.class, true);
    }

    public DefectLocationRequestBuilder withDefectId(Long id) {
        data.setDefectId(id);
        return this;
    }
}
