package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.DefectLocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DefectLocationEntityRepository extends JpaRepository<DefectLocationEntity, Long> {

    List<DefectLocationEntity> findAllByVehicleDefectEntityId(Long defectId);
}
