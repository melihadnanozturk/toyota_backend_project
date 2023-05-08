package com.mao.tytmistake.repository;

import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.lang.Nullable;

import java.util.Optional;

public interface VehicleEntityRepository extends JpaRepository<VehicleEntity, Long>, JpaSpecificationExecutor<VehicleEntity>, PagingAndSortingRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByChassisNumberAndIsDeletedIsFalse(String chassisNumber);

    Page<VehicleEntity> findAllByIsDeletedIsFalse(@Nullable Specification<VehicleEntity> spec, Pageable pageable);

}
