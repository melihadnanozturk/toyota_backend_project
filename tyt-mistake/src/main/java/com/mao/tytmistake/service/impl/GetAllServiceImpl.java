package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.LocationEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.utility.CreateVehicleDefectSpec;
import com.mao.tytmistake.model.utility.CreateVehicleSpec;
import com.mao.tytmistake.model.utility.HeaderUtility;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.GetAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllServiceImpl implements GetAllService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final AuthApiClient apiClient;

    @Override
    public Page<PageVehicleResponse> getAllVehicle(HttpHeaders headers, PageVehicleRequest request) {

        this.isClientValid(headers);

        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortBy());

        Specification<VehicleEntity> spec = CreateVehicleSpec.getAll(request);

        List<PageVehicleResponse> responses = vehicleEntityRepository.findAll(spec, pageable)
                .stream().map(PageVehicleResponse::vehicleEntityMappedPageResponse).toList();

        return new PageImpl<>(responses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<PageVehicleDefectResponse> getAllVehicleDefect(HttpHeaders headers, PageVehicleDefectRequest request) {
        this.isClientValid(headers);

        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortBy());

        //todo: will refactoring
        Specification<DefectEntity> spec = CreateVehicleDefectSpec.getAll(request);

        List<PageVehicleDefectResponse> page = vehicleDefectEntityRepository.findAll(spec, pageable)
                .stream().map(PageVehicleDefectResponse::vehicleDefectEntityMappedPageResponse).toList();

        return new PageImpl<>(page, pageable, request.getPageSize());
    }

    @Override
    public List<LocationsResponse> getAllLocations(HttpHeaders headers, Long defectId) {
        this.isClientValid(headers);

        List<LocationEntity> entities = defectLocationEntityRepository.findAllByVehicleDefectEntityId(defectId);

        return entities.stream().map(LocationsResponse::mappedLocationsResponse).toList();
    }

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);

        apiClient.validate(clientHeaders, Role.TEAM_LEAD);
    }
}
