package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.exception.AlreadyExistsException;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.repository.DefectEntityRepository;
import com.mao.tytmistake.service.DefectService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final DefectEntityRepository defectEntityRepository;

    @Override
    public PageDefectResponse getAllDefect() {
        List<DefectResponse> defectResponses = defectEntityRepository.findAll()
                .stream().map(DefectResponse::defectEntityMappedResponse).toList();

        return PageDefectResponse.builder()
                .defectResponses(defectResponses)
                .build();
    }

    @Override
    public DefectResponse addNewDefect(DefectRequest defectRequest) {
        checkDefectCodeBeforeInsert(defectRequest.getDefectCode());
        DefectEntity defectEntity = DefectRequest.requestMappedDefectEntity(defectRequest);
        DefectEntity saved = defectEntityRepository.save(defectEntity);
        return DefectResponse.defectEntityMappedResponse(saved);
    }

    @SneakyThrows
    @Override
    public DefectResponse updateDefect(UpdateDefectRequest updateDefectRequest) {
        DefectEntity defectEntity = controlsBeforeDefectCodeUpdate(updateDefectRequest);
        DefectEntity updatedEntity = updateEntity(defectEntity, updateDefectRequest.getDefectRequest());
        DefectEntity savedEntity = defectEntityRepository.save(updatedEntity);
        return DefectResponse.defectEntityMappedResponse(savedEntity);
    }

    @Override
    public Long removeDefect(Long id) {
        DefectEntity defectEntity = getById(id);
        defectEntity.setIsDeleted(true);
        return defectEntityRepository.save(defectEntity).getId();
    }

    @Override
    public DefectEntity getById(Long id) {
        return checkExists(id);
    }

    private DefectEntity checkExists(Long id) {
        return defectEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));
    }

    private DefectEntity controlsBeforeDefectCodeUpdate(UpdateDefectRequest updateDefectRequest) {
        DefectEntity entity = checkExists(updateDefectRequest.getId());
        String defectCode = updateDefectRequest.getDefectRequest().getDefectCode();

        DefectEntity found = defectEntityRepository.findByDefectCode(defectCode).orElse(null);

        if (found != null && (!entity.getId().equals(found.getId())))
            throw new AlreadyExistsException(defectCode);

        return entity;
    }

    private void checkDefectCodeBeforeInsert(String defectCode) {
        if (defectEntityRepository.findByDefectCode(defectCode).isPresent()) {
            throw new AlreadyExistsException(defectCode);
        }
    }

    private DefectEntity updateEntity(DefectEntity defectEntity, DefectRequest defectRequest) {
        defectEntity.setDefectDesc(defectRequest.getDefectDesc());
        defectEntity.setDefectCode(defectRequest.getDefectCode());
        return defectEntity;
    }
}
