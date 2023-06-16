package com.mao.tytmistake.model.utility;

import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

public class CreateVehicleDefectSpec {

    private CreateVehicleDefectSpec() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<DefectEntity> getAll(PageVehicleDefectRequest request) {
        return getIsNotDeleted()
                .and(getByVehicleId(request.getVehicleId()))
                .and(getByDefect(request.getDefect()));
    }

    private static Specification<DefectEntity> getByVehicleId(Long id) {
        return (root, query, criteriaBuilder) -> {
            Join<DefectEntity, VehicleEntity> vehicleJoin = root.join("vehicle");
            return id == null ? null : criteriaBuilder.equal(vehicleJoin.get("id"), id);
        };
    }

    private static Specification<DefectEntity> getIsNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"));
    }

    private static Specification<DefectEntity> getByDefect(Defect defect) {
        return (root, query, criteriaBuilder) ->
                defect == null ? null : criteriaBuilder.equal(root.get("defect"), defect);
    }
}
