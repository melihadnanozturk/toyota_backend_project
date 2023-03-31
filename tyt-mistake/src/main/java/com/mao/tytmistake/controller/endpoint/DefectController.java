package com.mao.tytmistake.controller.endpoint;


import com.mao.tytmistake.model.domain.Defect;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.service.DefectService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class DefectController {

    private final DefectService defectService;

    @GetMapping
    public List<DefectEntity> getAllDefect() {
        return defectService.getAllDefect();
    }

    @PostMapping
    public DefectEntity addNewDefect(@RequestBody Defect defect) {
        return defectService.addNewDefect(defect);
    }

    @DeleteMapping
    public String removeDefect(Long id) {
        defectService.removeDefect(id);
        return id + " was deleted";
    }

    @PutMapping("/{id}")
    public DefectEntity updateDefect(@PathVariable Long id, @RequestBody Defect defect) {
        return defectService.updateDefect(id, defect);
    }

}
