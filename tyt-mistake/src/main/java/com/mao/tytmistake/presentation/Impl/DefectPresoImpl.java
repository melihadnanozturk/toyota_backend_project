package com.mao.tytmistake.presentation.Impl;

import com.mao.tytmistake.model.domain.Defect;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.presentation.DefectPreso;
import com.mao.tytmistake.presentation.repository.DefectEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectPresoImpl implements DefectPreso {

    private final DefectEntityRepository defectEntityRepository;

    @Override
    public List<DefectEntity> getAllDefect() {
        return defectEntityRepository.findAll();
    }

    @Override
    public DefectEntity addNewDefect(Defect defect) {
        DefectEntity defectEntity = new DefectEntity();
        defectEntity.setDefectCode(defect.getDefectCode());
        defectEntity.setDefectDesc(defect.getDefectDesc());
        return defectEntityRepository.save(defectEntity);
    }

    @Override
    public void removeDefect(Long id) {
        defectEntityRepository.deleteById(id);
    }

    @Override
    public DefectEntity updateDefect(Long id, Defect defect) {
        return null;
    }
}
