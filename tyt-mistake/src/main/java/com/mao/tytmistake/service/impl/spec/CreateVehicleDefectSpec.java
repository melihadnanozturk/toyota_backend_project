package com.mao.tytmistake.service.impl.spec;

import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CreateVehicleDefectSpec {

    public static Specification<VehicleDefectEntity> getAll(PageVehicleDefectRequest request) {
        return getIsNotDeleted()
                .and(getDeletedJoin())
                .and(getByVehicleId(request.getVehicleId()))
                .and(getByDefect(request.getDefect()));
    }

    private static Specification<VehicleDefectEntity> getByVehicleId(Long id) {
        return (root, query, criteriaBuilder) -> {
            Join<VehicleDefectEntity, VehicleEntity> vehicleJoin = root.join("vehicle");
            return id == null ? null : criteriaBuilder.equal(vehicleJoin.get("id"), id);
        };
    }

    private static Specification<VehicleDefectEntity> getDeletedJoin() {
        return (root, query, criteriaBuilder) -> {
            Join<VehicleDefectEntity, VehicleEntity> vehicleJoin = root.join("vehicle");
            return criteriaBuilder.isFalse(vehicleJoin.get("isDeleted"));
        };
    }

    private static Specification<VehicleDefectEntity> getIsNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"));
    }

    private static Specification<VehicleDefectEntity> getByDefect(Defect defect) {
        return (root, query, criteriaBuilder) ->
                defect == null ? null : criteriaBuilder.equal(root.get("defect"), defect);
    }
}
