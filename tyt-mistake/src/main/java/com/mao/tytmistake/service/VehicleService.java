package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.exception.NotFoundException;
import org.springframework.http.HttpHeaders;

/**
 * Service interface for vehicle functionality.
 */
public interface VehicleService {

    /**
     * Adds a new vehicle based on provided information.
     *
     * @param headers        HttpHeaders containing client information.
     * @param vehicleRequest VehicleRequest object containing vehicle details.
     * @return VehicleResponse object representing newly added vehicle.
     */
    VehicleResponse addNewVehicle(HttpHeaders headers, VehicleRequest vehicleRequest);

    /**
     * Removes a vehicle based on provided ID.
     *
     * @param headers HttpHeaders containing client information.
     * @param id      ID of the vehicle to remove.
     * @return ID of the removed vehicle.
     * @throws NotFoundException if vehicle is not found.
     */
    Long removeVehicle(HttpHeaders headers, Long id);

    /**
     * Updates information of an existing vehicle.
     *
     * @param headers        HttpHeaders containing client information.
     * @param id             ID of vehicle to update.
     * @param vehicleRequest VehicleRequest object containing updated vehicle details.
     * @return VehicleResponse object representing updated vehicle.
     */
    VehicleResponse updateVehicle(HttpHeaders headers, Long id, VehicleRequest vehicleRequest);

    /**
     * Retrieves a vehicle by its ID.
     *
     * @param id ID of vehicle to retrieve.
     * @return VehicleEntity with specified ID.
     */
    VehicleEntity getById(Long id);
}