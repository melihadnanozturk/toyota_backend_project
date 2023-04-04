package com.mao.tytmistake.controller.endpoint;


import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.service.DefectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/defect")
@RequiredArgsConstructor
public class DefectController {

    private final DefectService defectService;

    @GetMapping
    public PageDefectResponse getAllDefect() {
        return defectService.getAllDefect();
    }

    @PostMapping
    public DefectResponse addNewDefect(@RequestBody @Valid DefectRequest defectRequest) {
        return defectService.addNewDefect(defectRequest);
    }

    @DeleteMapping
    public void removeDefect(Long id) {
        defectService.removeDefect(id);
    }

    @PutMapping()
    public DefectResponse updateDefect(@RequestBody UpdateDefectRequest updateDefectRequest) {
        return defectService.updateDefect(updateDefectRequest);
    }

}
