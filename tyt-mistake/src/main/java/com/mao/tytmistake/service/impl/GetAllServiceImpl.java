package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.PageDefectLocationResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.impl.spec.CreateVehicleDefectSpec;
import com.mao.tytmistake.service.impl.spec.CreateVehicleSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAllServiceImpl implements GetAllService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;

    @Override
    public Page<PageVehicleResponse> getAllVehicle(PageVehicleRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortBy());

        Specification<VehicleEntity> spec = CreateVehicleSpec.getAll(request);

        //TODO: burada sıralma yaparken defectNumbers a göre de sıralama yapsa güzel olur
        //todo: will refactoring
        List<PageVehicleResponse> responses = vehicleEntityRepository.findAll(spec, pageable)
                .stream().map(PageVehicleResponse::vehicleEntityMappedPageResponse).toList();

        this.mappedDefectNumbers(responses);

        return new PageImpl<>(responses, pageable, pageable.getPageSize());
    }

    @Override
    public Page<PageVehicleDefectResponse> getAllVehicleDefect(PageVehicleDefectRequest request) {
        Pageable pageable = PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortBy());

        //todo: will refactoring
        Specification<VehicleDefectEntity> spec = CreateVehicleDefectSpec.getAll(request);

        List<PageVehicleDefectResponse> page = vehicleDefectEntityRepository.findAll(spec, pageable)
                .stream().map(PageVehicleDefectResponse::vehicleDefectEntityMappedPageResponse).toList();

        return new PageImpl<>(page, pageable, request.getPageSize());
    }

    @Override
    public PageDefectLocationResponse findAll() {
        return null;
    }

    private void mappedDefectNumbers(List<PageVehicleResponse> responses) {
        responses.forEach(repsonse -> repsonse.setDefectNumbers(this.getDefectNumbersByVehicleId(repsonse.getId())));
    }

    private Integer getDefectNumbersByVehicleId(Long vehicleId) {
        return vehicleDefectEntityRepository.findAllByVehicleId(vehicleId).size();
    }
}
