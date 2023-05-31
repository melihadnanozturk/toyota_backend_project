package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.DefectLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DefectLocationEntityRepository extends JpaRepository<DefectLocationEntity, Long> {

    @Query(value = "SELECT * FROM tt_defect_location lc WHERE lc.vehicle_defect_id = :defectId AND lc.is_deleted = false", nativeQuery = true)
    List<DefectLocationEntity> findAllByVehicleDefectEntityId(@Param("defectId") Long defectId);

    Optional<DefectLocationEntity> findByIdAndIsDeletedIsFalse(Long id);
}
