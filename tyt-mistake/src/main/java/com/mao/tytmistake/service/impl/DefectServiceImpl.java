package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.repository.DefectEntityRepository;
import com.mao.tytmistake.service.DefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final DefectEntityRepository defectEntityRepository;

    @Override
    public PageDefectResponse getAllDefect() {
        return PageDefectResponse.builder()
                .defectResponses(defectEntityRepository.findAll()
                        .stream().map(this::mapToResponse).toList())
                .build();
    }

    @Override
    public DefectResponse addNewDefect(DefectRequest defectRequest) {
        checkDefectCodeBeforeInsert(defectRequest.getDefectCode());
        DefectEntity defectEntity = mapToEntity(defectRequest);
        return mapToResponse(defectEntityRepository.save(defectEntity));
    }

    @Override
    public DefectEntity getById(Long id) {
        return checkExists(id);
    }

    @SneakyThrows
    @Override
    public DefectResponse updateDefect(UpdateDefectRequest updateDefectRequest) {
        DefectEntity defectEntity = updateEntity(controlsBeforeDefectCodeUpdate(updateDefectRequest), updateDefectRequest.getDefectRequest());

        return mapToResponse(defectEntityRepository.save(defectEntity));
    }

    @Override
    public void removeDefect(Long id) {
        defectEntityRepository.deleteById(id);
    }

    @SneakyThrows
    private DefectEntity checkExists(Long id) {
        return defectEntityRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @SneakyThrows
    private DefectEntity controlsBeforeDefectCodeUpdate(UpdateDefectRequest updateDefectRequest) {
        DefectEntity entity = checkExists(updateDefectRequest.getId());

        DefectEntity found = defectEntityRepository.findByDefectCode(updateDefectRequest.getDefectRequest().getDefectCode()).orElse(null);

        if (found != null && (!entity.getId().equals(found.getId())))
            throw new Exception("Zaten var olan bir defect codunu girmeye çalışıyorsunuz");

        return entity;
    }

    @SneakyThrows
    private void checkDefectCodeBeforeInsert(String defectCode) {
        if (defectEntityRepository.findByDefectCode(defectCode).isPresent()) {
            throw new Exception("Bu defectCode dan zaten var");
        }
    }

    //todo: mapStruct will add
    private DefectEntity updateEntity(DefectEntity defectEntity, DefectRequest defectRequest) {
        defectEntity.setDefectDesc(defectRequest.getDefectDesc());
        defectEntity.setDefectCode(defectRequest.getDefectCode());
        return defectEntity;
    }

    private DefectEntity mapToEntity(DefectRequest defectRequest) {
        return DefectEntity.builder()
                .defectCode(defectRequest.getDefectCode())
                .defectDesc(defectRequest.getDefectDesc())
                .build();
    }

    private DefectResponse mapToResponse(DefectEntity defectEntity) {
        return DefectResponse.builder()
                .id(defectEntity.getId())
                .defectCode(defectEntity.getDefectCode())
                .defectDesc(defectEntity.getDefectDesc())
                .build();

    }
}
