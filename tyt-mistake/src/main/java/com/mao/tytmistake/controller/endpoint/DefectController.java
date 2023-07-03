package com.mao.tytmistake.controller.endpoint;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.response.BaseResponse;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.service.DefectService;
import com.mao.tytmistake.service.GetAllService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * This Controller used to create, update, delete and retrieve defects
 */
@RestController
@RequestMapping("/vd")
@RequiredArgsConstructor
public class DefectController {

    private final DefectService defectService;
    private final GetAllService getAllService;

    /**
     * Retrieve Defects
     *
     * @param headers           UserName, Bearer Token
     * @param pageDefectRequest Request for pagination
     * @return PageVehicleDefectResponse contain defect information
     */
    @GetMapping
    public BaseResponse<List<PageDefectResponse>> getAllDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody PageDefectRequest pageDefectRequest) {
        Page<PageDefectResponse> page = getAllService.getAllDefect(headers, pageDefectRequest);
        return BaseResponse.isSuccess(page.getContent());
    }

    /**
     * Retrieve Defect Image
     *
     * @param headers UserName, Bearer Token
     * @param id      Defect entity id
     * @return byte[]           Defect entity image
     */
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getDefectImage(@RequestHeader HttpHeaders headers,
                                                 @PathVariable Long id) {

        byte[] imageData = getAllService.getDefectImage(headers, id);

        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(imageData);
    }

    /**
     * Create new defect
     *
     * @param headers       UserName, Bearer Token
     * @param defectRequest Defect information to register
     * @return VehicleDefectResponse with created defect information
     */
    @PostMapping
    public BaseResponse<DefectResponse> addNewDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody DefectRequest defectRequest) {
        DefectResponse response = defectService.addNewDefect(headers, defectRequest);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Add defect images
     *
     * @param headers   UserName, Bearer Token
     * @param imageFile Defect image file
     * @return Long  Picture added defect id
     */
    @PostMapping("/{id}")
    public BaseResponse<Long> addNewDefectImage(
            @RequestHeader HttpHeaders headers,
            @RequestParam("imageFile") MultipartFile imageFile,
            @PathVariable Long id) {
        Long response = defectService.addDefectImage(headers, imageFile, id);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Update exits defect
     *
     * @param headers              UserName, Bearer Token
     * @param vehicleDefectRequest Defect information to update
     * @param defectId             Defect id to update
     * @return VehicleDefectResponse with updated defect information
     */
    @PutMapping("/{defectId}")
    public BaseResponse<DefectResponse> updateDefect(
            @RequestHeader HttpHeaders headers,
            @RequestBody UpdateDefectRequest vehicleDefectRequest,
            @PathVariable Long defectId) {
        DefectResponse response = defectService.updateDefect(headers, vehicleDefectRequest, defectId);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Update exits defect image
     *
     * @param headers   UserName, Bearer Token
     * @param imageFile Defect image to defect image update
     * @return Long with updated defect information
     */
    @PatchMapping("/img/{defectId}")
    public BaseResponse<Long> updateDefectImage(
            @RequestHeader HttpHeaders headers,
            @RequestParam("imageFile") MultipartFile imageFile,
            @PathVariable Long defectId) {
        Long response = defectService.updateDefectImage(headers, imageFile, defectId);
        return BaseResponse.isSuccess(response);
    }

    /**
     * Remove exits defect
     *
     * @param headers UserName, Bearer Token
     * @return Long that removed defect id
     */
    @DeleteMapping("/{id}")
    public BaseResponse<Long> removeDefect(
            @RequestHeader HttpHeaders headers,
            @PathVariable Long id) {
        Long removedId = defectService.deleteDefect(headers, id);
        return BaseResponse.isSuccess(removedId);
    }
}
