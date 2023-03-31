package com.mao.tytmistake.presentation;

import com.mao.tytmistake.model.domain.Defect;
import com.mao.tytmistake.model.entity.DefectEntity;

import java.util.List;

public interface DefectPreso {

    List<DefectEntity> getAllDefect();

    void removeDefect(Long id);

    DefectEntity updateDefect(Long id, Defect defect);

    DefectEntity addNewDefect(Defect defect);
}
