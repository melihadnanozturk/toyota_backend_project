package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.DefectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

/**
 * This repository interface for CRUD operations on the DefectEntity objects in the database.
 */
public interface VehicleDefectEntityRepository extends JpaRepository<DefectEntity, Long>, JpaSpecificationExecutor<DefectEntity> {

    /**
     * Retrieves non-deleted DefectEntity about to defectId
     *
     * @param defectId non-deleted DefectEntity id
     * @return DefectEntity found about to defectId
     */
    Optional<DefectEntity> findByIdAndIsDeletedIsFalse(Long defectId);
}
