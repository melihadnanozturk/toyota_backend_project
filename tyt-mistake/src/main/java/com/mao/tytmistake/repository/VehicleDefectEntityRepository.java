package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.DefectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface VehicleDefectEntityRepository extends JpaRepository<DefectEntity, Long>, JpaSpecificationExecutor<DefectEntity> {

    List<DefectEntity> findAllByVehicleId(Long id);

    Optional<DefectEntity> findByIdAndIsDeletedIsFalse(Long id);
}
