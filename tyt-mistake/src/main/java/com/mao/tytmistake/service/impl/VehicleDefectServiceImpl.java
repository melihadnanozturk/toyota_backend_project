package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.VehicleDefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleDefectServiceImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleServiceImpl vehicleService;

    @SneakyThrows
    @Override
    public BaseResponse<VehicleDefectResponse> addNewVehicleDefect(VehicleDefectRequest vehicleDefectRequest) {
        VehicleDefectEntity vehicleDefectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);

        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());
        vehicleDefectEntity.setVehicle(vehicleEntity);

        VehicleDefectResponse response = VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
        return BaseResponse.isSuccess(response);
    }

    @Override
    public BaseResponse<VehicleDefectResponse> updateVehicleDefect(UpdateVehicleDefectRequest request, Long id) {
        VehicleDefectEntity vehicleDefectEntity = this.checkVehicleDefectEntityIsExists(id);
        checkImageIsExist(vehicleDefectEntity, request.getDefectImage());
        vehicleDefectEntity.setVehicleDefectDesc(request.getDefectDesc());
        vehicleDefectEntity.setDefectImage(request.getDefectImage());
        VehicleDefectResponse response = VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
        return BaseResponse.isSuccess(response);
    }

    @Override
    public VehicleDefectEntity getVehicleDefectEntityById(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    @Override
    public Integer getDefectNumbersByVehicleId(Long vehicleId) {
        return vehicleDefectEntityRepository.findAllByVehicleId(vehicleId).size();
    }

    //todo: kontrol et
    @Override
    public BaseResponse<Long> deleteVehicleDefect(Long id) {
        VehicleDefectEntity vehicleDefectEntity = checkVehicleDefectEntityIsExists(id);

        vehicleDefectEntity.setIsDeleted(true);
        vehicleDefectEntity.getDefectLocation().forEach(location -> location.setIsDeleted(true));

        Long removedId = vehicleDefectEntityRepository.save(vehicleDefectEntity).getId();
        return BaseResponse.isSuccess(removedId);
    }

    private VehicleDefectEntity checkVehicleDefectEntityIsExists(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private void checkImageIsExist(VehicleDefectEntity entity, String image) {
        if (image != null && !entity.getDefectImage().equals(image)) {
            entity.getDefectLocation().forEach(location -> location.setIsDeleted(true));
        }
    }

}
