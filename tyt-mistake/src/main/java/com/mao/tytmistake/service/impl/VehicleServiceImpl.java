package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.UpdateVehicleRequest;
import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.VehicleService;
import com.mao.tytmistake.service.impl.spec.CreateVehicleSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleEntityRepository vehicleEntityRepository;

    @Override
    public PageVehicleResponse getAllVehicle(PageVehicleRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortBy());

        Specification<VehicleEntity> spec = CreateVehicleSpec.getAll(request);

        //todo: will refactoring
        List<VehicleResponse> responses = vehicleEntityRepository.findAll(spec, pageable)
                .stream().map(VehicleResponse::vehicleEntityMappedResponse).toList();

        return PageVehicleResponse.builder()
                .vehicleResponseList(new PageImpl<>(responses, pageable, pageable.getPageSize()))
                .build();
    }

    @Override
    public VehicleResponse newVehicleAdd(VehicleRequest vehicleRequest) {
        checkChassisNumberBeforeInsert(vehicleRequest.getChassisNumber());

        VehicleEntity vehicleEntity = VehicleRequest.requestMappedVehicleEntity(vehicleRequest);
        VehicleEntity savedEntity = vehicleEntityRepository.save(vehicleEntity);
        return VehicleResponse.vehicleEntityMappedResponse(savedEntity);
    }

    @Override
    public VehicleResponse updateVehicle(UpdateVehicleRequest vehicleRequest) {
        VehicleEntity vehicleEntity = checkVehicleEntityBeforeUpdate(vehicleRequest);
        VehicleEntity updatedEntity = setVehicle(vehicleEntity, vehicleRequest.getVehicleRequest());
        VehicleEntity savedEntity = vehicleEntityRepository.save(updatedEntity);
        return VehicleResponse.vehicleEntityMappedResponse(savedEntity);
    }

    @Override
    public Long removeVehicle(Long id) {
        VehicleEntity vehicleEntity = getById(id);
        vehicleEntity.setIsDeleted(true);
        return vehicleEntityRepository.save(vehicleEntity).getId();
    }

    @Override
    public VehicleEntity getById(Long id) {
        return vehicleEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void checkChassisNumberBeforeInsert(String chassisNumber) {
        if (vehicleEntityRepository.findByChassisNumberAndIsDeletedIsFalse(chassisNumber).isPresent()) {
            throw new AlreadyExistsException(chassisNumber);
        }
    }

    private VehicleEntity checkVehicleEntityBeforeUpdate(UpdateVehicleRequest vehicleRequest) {
        VehicleEntity byId = getById(vehicleRequest.getId());
        VehicleEntity byChassisNumber = vehicleEntityRepository
                .findByChassisNumberAndIsDeletedIsFalse(vehicleRequest.getVehicleRequest().getChassisNumber()).orElse(null);

        if (byChassisNumber != null && (!byId.getId().equals(byChassisNumber.getId()))) {
            throw new AlreadyExistsException(byChassisNumber.getChassisNumber());
        }

        return byId;
    }

    private VehicleEntity setVehicle(VehicleEntity vehicleEntity, VehicleRequest vehicleRequest) {
        vehicleEntity.setColour(vehicleRequest.getColour());
        vehicleEntity.setChassisNumber(vehicleRequest.getChassisNumber());
        vehicleEntity.setModel(vehicleRequest.getModel());
        return vehicleEntity;
    }

}
