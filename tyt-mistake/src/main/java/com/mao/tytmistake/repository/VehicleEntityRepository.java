package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity> {

    Optional<VehicleEntity> findByChassisNumberAndIsDeletedIsFalse(String chassisNumber);

    Optional<VehicleEntity> findByIdAndIsDeletedIsFalse(Long id);

}
