package com.mao.tytmistake.service.impl;

import com.mao.tytmistake.client.AuthApiClient;
import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.request.page.TytPageRequest;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.model.entity.DefectEntity;
import com.mao.tytmistake.model.entity.LocationEntity;
import com.mao.tytmistake.model.entity.VehicleEntity;
import com.mao.tytmistake.model.entity.enums.Role;
import com.mao.tytmistake.model.exception.NotFoundException;
import com.mao.tytmistake.model.utility.CreateDefectSpec;
import com.mao.tytmistake.model.utility.CreateVehicleSpec;
import com.mao.tytmistake.model.utility.HeaderUtility;
import com.mao.tytmistake.repository.DefectLocationEntityRepository;
import com.mao.tytmistake.repository.VehicleDefectEntityRepository;
import com.mao.tytmistake.repository.VehicleEntityRepository;
import com.mao.tytmistake.service.GetAllService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for retrieving all entities.
 */
@Service
@RequiredArgsConstructor
public class GetAllServiceImpl implements GetAllService {

    private final VehicleEntityRepository vehicleEntityRepository;
    private final VehicleDefectEntityRepository vehicleDefectEntityRepository;
    private final DefectLocationEntityRepository defectLocationEntityRepository;
    private final AuthApiClient apiClient;

    private final Logger logger = LogManager.getLogger(GetAllServiceImpl.class);

    /**
     * Retrieves a page of vehicle entities based on the specified request parameters.
     *
     * @param headers the HttpHeaders containing the request headers
     * @param request the PageVehicleRequest specifying the page parameters
     * @return a Page of PageVehicleResponse objects
     */
    @Override
    public Page<PageVehicleResponse> getAllVehicle(HttpHeaders headers, PageVehicleRequest request) {
        this.isClientValid(headers);

        Pageable pageable = TytPageRequest.createPageRequest(request);
        Specification<VehicleEntity> spec = CreateVehicleSpec.getAll(request);

        List<PageVehicleResponse> responses = vehicleEntityRepository.findAll(spec, pageable)
                .stream().map(PageVehicleResponse::vehicleEntityMappedPageResponse).toList();

        return new PageImpl<>(responses, pageable, pageable.getPageSize());
    }

    /**
     * Retrieves a page of defect entities based on the specified request parameters.
     *
     * @param headers the HttpHeaders containing the request headers
     * @param request the PageDefectRequest specifying the page parameters
     * @return a Page of PageDefectResponse objects
     */
    @Override
    public Page<PageDefectResponse> getAllDefect(HttpHeaders headers, PageDefectRequest request) {
        this.isClientValid(headers);

        Pageable pageable = TytPageRequest.createPageRequest(request);
        Specification<DefectEntity> spec = CreateDefectSpec.getAll(request);

        List<PageDefectResponse> page = vehicleDefectEntityRepository.findAll(spec, pageable)
                .stream().map(PageDefectResponse::vehicleDefectEntityMappedPageResponse).toList();

        return new PageImpl<>(page, pageable, request.getPageSize());
    }

    /**
     * Retrieves all locations associated with the specified defect ID.
     *
     * @param headers  the HttpHeaders containing the request headers
     * @param defectId the ID of the defect to retrieve the locations for
     * @return a list of LocationsResponse objects
     */
    @Override
    public List<LocationsResponse> getAllLocations(HttpHeaders headers, Long defectId) {
        this.isClientValid(headers);

        List<LocationEntity> entities = defectLocationEntityRepository.findAllByVehicleDefectEntityId(defectId);

        return entities.stream().map(LocationsResponse::mappedLocationsResponse).toList();
    }

    /**
     * Retrieves the image of a defect.
     *
     * @param headers HTTP headers containing client information.
     * @param id      ID of defect.
     * @return Byte array representation of the defect image.
     * @throws NotFoundException if defect with given ID is not found.
     */
    @Override
    public byte[] getDefectImage(HttpHeaders headers, Long id) {
        this.isClientValid(headers);
        DefectEntity entity = vehicleDefectEntityRepository.findByIdAndIsDeletedIsFalse(id)
                .orElseThrow(() -> new NotFoundException(id.toString()));

        return entity.getDefectImage();
    }

    /**
     * Validates the client's authorization based on the request headers.
     *
     * @param headers the HttpHeaders containing the request headers
     */

    private void isClientValid(HttpHeaders headers) {
        HttpHeaders clientHeaders = HeaderUtility.createHeader(headers);
        String userName = HeaderUtility.getUser(headers);

        logger.atInfo().log("User with NAME {} be directed Authorization", userName);
        apiClient.validate(clientHeaders, Role.TEAM_LEAD);
    }
}
