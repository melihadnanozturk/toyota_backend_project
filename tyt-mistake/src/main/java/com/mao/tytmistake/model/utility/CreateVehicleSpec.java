package com.mao.tytmistake.model.utility;

import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utility class for creating specifications to filter vehicle entities.
 */
public class CreateVehicleSpec {

    private CreateVehicleSpec() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Creates a specification for filtering vehicle entities based on the provided criteria.
     *
     * @param pageVehicleRequest the page request containing the filter criteria
     * @return a Specification object for filtering vehicle entities
     */
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
