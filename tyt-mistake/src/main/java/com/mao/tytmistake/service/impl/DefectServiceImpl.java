package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.model.domain.Defect;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.presentation.DefectPreso;
import com.mao.tytmistake.service.DefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefectServiceImpl implements DefectService {

    private final DefectPreso defectPreso;

    @Override
    public List<DefectEntity> getAllDefect() {
        return defectPreso.getAllDefect();
    }

    @Override
    public DefectEntity addNewDefect(Defect defect) {
        return defectPreso.addNewDefect(defect);
    }

    @Override
    public void removeDefect(Long id) {
        defectPreso.removeDefect(id);
    }

    @Override
    public DefectEntity updateDefect(Long id, Defect defect) {
        return null;
    }
}
