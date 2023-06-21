package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * This repository interface for CRUD operations on the LocationEntity objects in the database.
 */
public interface DefectLocationEntityRepository extends JpaRepository<LocationEntity, Long> {

    /**
     * Retrieves non-deleted LocationEntities about to defectId
     *
     * @param defectId non-deleted LocationEntity id
     * @return List<LocationEntity> found all entities about to defectId
     */
    @Query(value = "SELECT * FROM tt_location lc WHERE lc.defect_Id = :defectId AND lc.is_Deleted = false", nativeQuery = true)
    List<LocationEntity> findAllByVehicleDefectEntityId(@Param("defectId") Long defectId);

    /**
     * Retrieves non-deleted LocationEntity about to locationId
     *
     * @param locationId non-deleted LocationEntity locationId
     * @return LocationEntity found about to locationId
     */
    Optional<LocationEntity> findByIdAndIsDeletedIsFalse(Long locationId);
}
