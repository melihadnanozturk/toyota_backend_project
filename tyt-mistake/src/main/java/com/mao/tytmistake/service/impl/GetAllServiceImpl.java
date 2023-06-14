package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.page.PageVehicleDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.entity.DefectLocationEntity;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.impl.spec.CreateVehicleDefectSpec;
import com.mao.tytmistake.service.impl.spec.CreateVehicleSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GetAllServiceImpl implements GetAllService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final AuthApiClient apiClient;

    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";

    @Override
    public Page<PageVehicleResponse> getAllVehicle(HttpHeaders headers, PageVehicleRequest request) {

        this.isValidRequest(headers);

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
    public Page<PageVehicleDefectResponse> getAllVehicleDefect(HttpHeaders headers, PageVehicleDefectRequest request) {
        this.isValidRequest(headers);

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
    public List<LocationsResponse> getAllLocations(HttpHeaders headers, Long defectId) {
        this.isValidRequest(headers);

        List<DefectLocationEntity> entities = defectLocationEntityRepository.findAllByVehicleDefectEntityId(defectId);

        return entities.stream().map(LocationsResponse::mappedLocationsResponse).toList();
    }


    private void isValidRequest(HttpHeaders headers) {
        Map<String, String> info = getHeaderInfo(headers);

        apiClient.validate(info.get(USER_NAME), info.get(TOKEN), Role.TEAM_LEAD);
    }

    private void mappedDefectNumbers(List<PageVehicleResponse> responses) {
        responses.forEach(repsonse -> repsonse.setDefectNumbers(this.getDefectNumbersByVehicleId(repsonse.getId())));
    }

    private Integer getDefectNumbersByVehicleId(Long vehicleId) {
        return vehicleDefectEntityRepository.findAllByVehicleId(vehicleId).size();
    }

    private Map<String, String> getHeaderInfo(HttpHeaders headers) {
        Map<String, String> infos = new HashMap<>();

        String userName = Objects.requireNonNull(headers.get(USER_NAME)).get(0);
        String token = Objects.requireNonNull(headers.get(AUTHORIZATION)).get(0);

        infos.put(USER_NAME, userName);
        infos.put(TOKEN, token);

        return infos;

    }
}
