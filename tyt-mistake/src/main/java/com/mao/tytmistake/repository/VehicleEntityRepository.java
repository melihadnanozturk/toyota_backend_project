package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByChassisNumberAndIsDeletedIsFalse(String chassisNumber);
}
