package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface VehicleDefectEntityRepository extends JpaRepository<VehicleDefectEntity, Long>, JpaSpecificationExecutor<VehicleDefectEntity> {

    List<VehicleDefectEntity> findAllByVehicleId(Long id);
}
