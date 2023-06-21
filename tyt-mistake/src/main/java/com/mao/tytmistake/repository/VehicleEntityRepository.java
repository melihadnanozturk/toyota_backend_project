package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * This repository interface for CRUD operations on the VehicleEntity objects in the database.
 */
public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity> {

    /**
     * Retrieves non-deleted VehicleEntity about to chassisNumber
     *
     * @param chassisNumber non-deleted VehicleEntity chassisNumber
     * @return VehicleEntity found about to chassisNumber
     */
    Optional<VehicleEntity> findByChassisNumberAndIsDeletedIsFalse(String chassisNumber);

    /**
     * Retrieves non-deleted VehicleEntity about to vehicleId
     *
     * @param vehicleId non-deleted VehicleEntity id
     * @return VehicleEntity found about to vehicleId
     */
    Optional<VehicleEntity> findByIdAndIsDeletedIsFalse(Long vehicleId);

}
