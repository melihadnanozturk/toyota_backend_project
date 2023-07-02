package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.multipart.MultipartFile;

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
     * Adds an image to a defect.
     *
     * @param headers       HTTP headers containing client information.
     * @param multipartFile Image file to be added to the defect.
     * @param id            ID of the defect.
     * @return ID of the defect after adding the image.
     */
    Long addDefectImage(HttpHeaders headers, MultipartFile multipartFile, Long id);

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

    /**
     * Updates the image of a defect.
     *
     * @param headers   HTTP headers containing client information.
     * @param imageFile New image file to be updated for defect.
     * @param defectId  ID of defect.
     * @return Updated ID of  defect.
     */
    Long updateDefectImage(HttpHeaders headers, MultipartFile imageFile, Long defectId);
}
