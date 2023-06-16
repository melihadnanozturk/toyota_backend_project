package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DefectLocationEntityRepository extends JpaRepository<LocationEntity, Long> {

    @Query(value = "SELECT * FROM tt_location lc WHERE lc.defect_Id = :defectId AND lc.is_Deleted = false", nativeQuery = true)
    List<LocationEntity> findAllByVehicleDefectEntityId(@Param("defectId") Long defectId);

    Optional<LocationEntity> findByIdAndIsDeletedIsFalse(Long id);
}
