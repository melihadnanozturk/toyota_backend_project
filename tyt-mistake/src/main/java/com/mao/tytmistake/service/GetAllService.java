package com.mao.tytmistake.service;

import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.LocationsResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Service interface for pagination functionality.
 */
public interface GetAllService {

    /**
     * Retrieves a page of vehicle entities based on the specified request parameters.
     *
     * @param headers the HttpHeaders containing the request headers
     * @param request the PageVehicleRequest specifying the page parameters
     * @return a Page of PageVehicleResponse objects
     */
    Page<PageVehicleResponse> getAllVehicle(HttpHeaders headers, PageVehicleRequest request);

    /**
     * Retrieves a page of defect entities based on the specified request parameters.
     *
     * @param headers the HttpHeaders containing the request headers
     * @param request the PageDefectRequest specifying the page parameters
     * @return a Page of PageDefectResponse objects
     */
    Page<PageDefectResponse> getAllDefect(HttpHeaders headers, PageDefectRequest request);

    /**
     * Retrieves all locations associated with the specified defect ID.
     *
     * @param headers  the HttpHeaders containing the request headers
     * @param defectId the ID of the defect to retrieve the locations for
     * @return a list of LocationsResponse objects
     */
    List<LocationsResponse> getAllLocations(HttpHeaders headers, Long defectId);

    /**
     * Retrieves the image of a defect.
     *
     * @param headers HTTP headers containing client information.
     * @param id      ID of defect.
     * @return Byte array representation of the defect image.
     */
    byte[] getDefectImage(HttpHeaders headers, Long id);
}
