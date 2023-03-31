package com.mao.tytmistake.presentation.repository;

import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long> {
}
