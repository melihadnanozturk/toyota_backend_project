package com.mao.tytmistake.repository.page;

import com.mao.tytmistake.model.entity.VehicleEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * This repository interface for Pagination operations on the VehicleEntity objects in the database.
 */
public interface VehiclePagingAndSortingRepository extends PagingAndSortingRepository<VehicleEntity, Long> {
}
