package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import org.springframework.http.HttpHeaders;

/**
 * Service interface for defect functionality.
 */
public interface DefectService {

    /**
     * Adds a new defect for a vehicle.
     *
     * @param headers HttpHeaders containing request headers
     * @param request DefectRequest specifying defect information
     * @return a DefectResponse representing added defect
     */
    DefectResponse addNewDefect(HttpHeaders headers, DefectRequest request);

    /**
     * Deletes a defect.
     *
     * @param headers HttpHeaders containing request headers
     * @param id      ID of defect to be deleted
     * @return the ID of deleted defect
     */
    Long deleteDefect(HttpHeaders headers, Long id);

    /**
     * Updates an existing defect.
     *
     * @param headers HttpHeaders containing request headers
     * @param request UpdateDefectRequest specifying updated defect information
     * @param id      ID of defect to be updated
     * @return a DefectResponse representing updated defect
     */
    DefectResponse updateDefect(HttpHeaders headers, UpdateDefectRequest request, Long id);

    /**
     * Retrieves DefectEntity by its ID.
     *
     * @param id ID of the DefectEntity to retrieve.
     * @return DefectEntity with the specified ID.
     */
    DefectEntity getDefectEntityById(Long id);
}
