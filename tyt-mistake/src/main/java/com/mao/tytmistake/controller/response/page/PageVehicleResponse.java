package com.mao.tytmistake.controller.response.page;

import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Colour;
import com.mao.tytmistake.model.entity.enums.Model;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * This class is used to response vehicle information in page.
 * This class contains TerminalResponses
 * The class uses the Lombok annotations @Setter,  @Getter and @Builder to automatically generate getters and a builder method,@EqualsAndHashCode to equals process.
 */
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class PageVehicleResponse {

    private Long id;
    private Model model;
    private String chassisNumber;
    private Colour colour;
    private Integer defectNumbers;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    /**
     * Maps the VehicleEntity to the PageVehicleResponse
     *
     * @param vehicleEntity VehicleEntity to be mapped
     * @return TerminalResponse holding information mapped from terminalEntity
     */
    public static PageVehicleResponse vehicleEntityMappedPageResponse(VehicleEntity vehicleEntity) {
        return PageVehicleResponse.builder()
                .id(vehicleEntity.getId())
                .model(vehicleEntity.getModel())
                .chassisNumber(vehicleEntity.getChassisNumber())
                .colour(vehicleEntity.getColour())
                .defectNumbers(vehicleEntity.getDefect()
                        .stream()
                        .filter(defectEntity -> defectEntity.getIsDeleted().equals(false))
                        .toList().size())
                .createdAt(vehicleEntity.getCreatedAt())
                .updatedAt(vehicleEntity.getUpdatedAt())
                .build();
    }
}
