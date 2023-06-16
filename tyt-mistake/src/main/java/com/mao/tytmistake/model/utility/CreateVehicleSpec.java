package com.mao.tytmistake.model.utility;

import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import org.springframework.data.jpa.domain.Specification;

public class CreateVehicleSpec {

    private CreateVehicleSpec() {
        throw new IllegalStateException("Utility class");
    }

    public static Specification<VehicleEntity> getAll(PageVehicleRequest pageVehicleRequest) {
        return getByNotDeleted()
                .and(getByModel(pageVehicleRequest.getModel()))
                .and(getByColour(pageVehicleRequest.getColour()));
    }

    private static Specification<VehicleEntity> getByModel(Model model) {
        return (root, query, criteriaBuilder) ->
                model == null ? null : criteriaBuilder.equal(root.get("model"), model);
    }

    private static Specification<VehicleEntity> getByColour(Colour colour) {
        return (root, query, criteriaBuilder) ->
                colour == null ? null : criteriaBuilder.equal(root.get("colour"), colour);
    }

    private static Specification<VehicleEntity> getByNotDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isFalse(root.get("isDeleted"));
    }

}
