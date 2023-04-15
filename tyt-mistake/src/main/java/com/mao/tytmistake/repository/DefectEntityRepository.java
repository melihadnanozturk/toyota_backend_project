package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.DefectEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DefectEntityRepository extends JpaRepository<DefectEntity, Long> {

    Optional<DefectEntity> findByDefectCodeAndIsDeletedIsFalse(String defectCode);
}
