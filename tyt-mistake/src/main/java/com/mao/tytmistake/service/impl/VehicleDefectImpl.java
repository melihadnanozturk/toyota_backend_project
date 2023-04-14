package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.Locations;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.DefectLocationEntity;
import com.mao.tytmistake.model.entity.VehicleDefectEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.service.DefectService;
import com.mao.tytmistake.service.VehicleDefectService;
import com.mao.tytmistake.service.VehicleService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleDefectImpl implements VehicleDefectService {

    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final VehicleService vehicleService;
    private final DefectService defectService;


    @Override
    public PageVehicleDefectResponse getAllVehicleDefect() {
        List<VehicleDefectResponse> vehicleDefectResponses = vehicleDefectEntityRepository.findAll()
                .stream().map(VehicleDefectResponse::vehicleDefectEntityMappedResponse).toList();

        return PageVehicleDefectResponse.builder()
                .defectEntities(vehicleDefectResponses)
                .build();
    }

    @SneakyThrows
    @Override
    public VehicleDefectResponse addNewVehicleDefect(VehicleDefectRequest vehicleDefectRequest) {
        VehicleEntity vehicleEntity = vehicleService.getById(vehicleDefectRequest.getVehicleId());
        DefectEntity defectEntity = defectService.getById(vehicleDefectRequest.getDefectId());
        List<DefectLocationEntity> defectLocationEntity = locationsMappedEntities(vehicleDefectRequest.getLocaitons());

        VehicleDefectEntity vehicleDefectEntity = VehicleDefectRequest.responseMapToVehicleDefectEntity(vehicleDefectRequest);
        vehicleDefectEntity.setVehicle(vehicleEntity);
        vehicleDefectEntity.setDefect(defectEntity);
        vehicleDefectEntity.setDefectLocation(defectLocationEntity);

        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public VehicleDefectResponse updateVehicleDefect(UpdateVehicleDefectRequest vehicleDefectRequest) {
        VehicleDefectEntity vehicleDefectEntity = checkExist(vehicleDefectRequest.getId());
        vehicleDefectEntity.setDefectImage(vehicleDefectRequest.getVehicleDefectRequest().getDefectImage());
        return VehicleDefectResponse.vehicleDefectEntityMappedResponse(vehicleDefectEntityRepository.save(vehicleDefectEntity));
    }

    @Override
    public void deleteVehicleDefect(Long id) {
        vehicleDefectEntityRepository.deleteById(id);
    }

    @SneakyThrows
    private VehicleDefectEntity checkExist(Long id) {
        return vehicleDefectEntityRepository.findById(id).orElseThrow(() -> new Exception("Not found By ID"));
    }

    private List<DefectLocationEntity> locationsMappedEntities(List<Locations> locations) {
        return locations.stream().map(Locations::mappedDefectLocationEntity).toList();
    }
}
