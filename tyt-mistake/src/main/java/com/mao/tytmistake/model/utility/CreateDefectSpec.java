package com.mao.tytmistake.model.utility;

import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Defect;
import jakarta.persistence.criteria.Join;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utility class for creating specifications to filter defect entities.
 */
public class CreateDefectSpec {

    private CreateDefectSpec() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a specification for filtering vehicle entities based on the provided criteria.
     *
     * @param pageDefectRequest the page pageDefectRequest containing the filter criteria
     * @return a Specification object for filtering vehicle entities
     */
    public static Specification<DefectEntity> getAll(PageDefectRequest pageDefectRequest) {
        return getIsNotDeleted()
                .and(getByVehicleId(pageDefectRequest.getVehicleId()))
                .and(getByDefect(pageDefectRequest.getDefect()));
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
