package com.mao.tytmistake.controller.response;


import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * This class is used to response vehicle information.
 * This class contains information field about to vehicle.
 * The class uses the Lombok annotations @Getter,@Setter and @Builder to automatically generate getters, setters and a builders,@EqualsAndHashCode to equals process.
 */
@Getter
@Setter
@Builder
@EqualsAndHashCode
public class VehicleResponse {

    private Long id;
    private Model model;
    private String chassisNumber;
    private Colour colour;

    /**
     * Maps the VehicleEntity to the UserResponse
     *
     * @param vehicleEntity VehicleEntity to be mapped
     * @return VehicleResponse holding information mapped from vehicleEntity
     */
    public static VehicleResponse vehicleEntityMappedResponse(VehicleEntity vehicleEntity) {
        return VehicleResponse.builder()
                .id(vehicleEntity.getId())
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .build();
    }
}
