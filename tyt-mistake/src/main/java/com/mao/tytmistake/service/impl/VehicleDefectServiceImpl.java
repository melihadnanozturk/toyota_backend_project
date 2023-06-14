package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VehicleDefectServiceImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleServiceImpl vehicleService;
    private final AuthApiClient apiClient;


    private static final String USER_NAME = "userName";
    private static final String TOKEN = "token";
    private static final String AUTHORIZATION = "Authorization";

    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewVehicleDefect(HttpHeaders headers, VehicleDefectRequest vehicleDefectRequest) {
        this.isValidRequest(headers);
        VehicleDefectEntity vehicleDefectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());
        vehicleDefectEntity.setVehicle(vehicleEntity);

        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectResponse updateVehicleDefect(HttpHeaders headers, UpdateVehicleDefectRequest request, Long id) {
        this.isValidRequest(headers);

        VehicleDefectEntity vehicleDefectEntity = this.checkVehicleDefectEntityIsExists(id);
        checkImageIsExist(vehicleDefectEntity, request.getDefectImage());
        vehicleDefectEntity.setVehicleDefectDesc(request.getDefectDesc());
        vehicleDefectEntity.setDefectImage(request.getDefectImage());
        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectEntity getVehicleDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }

    @Override
    public Integer getDefectNumbersByVehicleId(Long vehicleId) {
        return vehicleDefectEntityRepository.findAllByVehicleId(vehicleId).size();
    }

    @Override
    public Long deleteVehicleDefect(HttpHeaders headers, Long id) {
        VehicleDefectEntity vehicleDefectEntity = checkVehicleDefectEntityIsExists(id);

        vehicleDefectEntity.setIsDeleted(true);
        vehicleDefectEntity.getDefectLocation().forEach(location -> location.setIsDeleted(true));

        return vehicleDefectEntityRepository.save(vehicleDefectEntity).getId();
    }

    private VehicleDefectEntity checkVehicleDefectEntityIsExists(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void checkImageIsExist(VehicleDefectEntity entity, String image) {
        if (image != null && !entity.getDefectImage().equals(image)) {
            entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        }
    }

    private void isValidRequest(HttpHeaders headers) {
        Map<String, String> info = getHeaderInfo(headers);

        apiClient.validate(info.get(USER_NAME), info.get(TOKEN), Role.OPERATOR);
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
