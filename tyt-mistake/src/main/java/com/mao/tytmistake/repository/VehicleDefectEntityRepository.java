package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleDefectEntityRepository extends JpaRepository<VehicleDefectEntity, Long>, JpaSpecificationExecutor<VehicleDefectEntity> {


}
