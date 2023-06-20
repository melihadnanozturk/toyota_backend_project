package com.mao.tytmistake.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequest;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.request.VehicleDefectRequest;
import com.mao.tytmistake.controller.request.VehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponseBuilder;
import com.mao.tytmistake.controller.response.VehicleDefectResponse;
import com.mao.tytmistake.controller.response.VehicleDefectResponseBuilder;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.VehicleDefectService;
import com.mao.tytmistake.service.impl.BaseControllerTests;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VehicleDefectController.class)
class VehicleDefectControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/vd";

    @MockBean
    private VehicleDefectService vehicleDefectService;
    @MockBean
    private GetAllService getAllService;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllDefect_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        PageDefectRequest testRequest = new PageDefectRequest();
        PageVehicleDefectResponse testResponse = new PageVehicleDefectResponseBuilder().build();
        Page<PageVehicleDefectResponse> testPage = new PageImpl<>(List.of(testResponse));

        Mockito.when(getAllService.getAllVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.any(PageDefectRequest.class)))
                .thenReturn(testPage);

        mockMvc.perform(get(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(getAllService, Mockito.times(1))
                .getAllVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.any(PageDefectRequest.class));
    }

    @Test
    void addNewDefect_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        VehicleDefectRequest testRequest = new VehicleDefectRequestBuilder().build();
        VehicleDefectResponse testResponse = new VehicleDefectResponseBuilder().build();

        Mockito.when(vehicleDefectService.addNewVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.any(VehicleDefectRequest.class)))
                .thenReturn(testResponse);

        mockMvc.perform(post(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(vehicleDefectService, Mockito.times(1))
                .addNewVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.any(VehicleDefectRequest.class));
    }

    @Test
    void updateDefect_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        UpdateVehicleDefectRequest testRequest = new UpdateVehicleDefectRequestBuilder().build();
        VehicleDefectResponse testResponse = new VehicleDefectResponseBuilder().build();

        Mockito.when(vehicleDefectService.updateVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.any(UpdateVehicleDefectRequest.class), Mockito.anyLong()))
                .thenReturn(testResponse);

        mockMvc.perform(put(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(vehicleDefectService, Mockito.times(1))
                .updateVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.any(UpdateVehicleDefectRequest.class), Mockito.anyLong());
    }

    @Test
    void deleteDefect_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();

        Mockito.when(vehicleDefectService.deleteVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.anyLong()))
                .thenReturn(testId);

        mockMvc.perform(delete(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(vehicleDefectService, Mockito.times(1))
                .deleteVehicleDefect(Mockito.any(HttpHeaders.class), Mockito.anyLong());
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}