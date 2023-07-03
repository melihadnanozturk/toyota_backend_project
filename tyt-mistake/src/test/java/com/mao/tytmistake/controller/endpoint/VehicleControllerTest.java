package com.mao.tytmistake.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.tytmistake.BaseControllerTests;
import com.mao.tytmistake.controller.request.VehicleRequest;
import com.mao.tytmistake.controller.request.VehicleRequestBuilder;
import com.mao.tytmistake.controller.request.page.PageVehicleRequest;
import com.mao.tytmistake.controller.response.PageVehicleResponseBuilder;
import com.mao.tytmistake.controller.response.VehicleResponse;
import com.mao.tytmistake.controller.response.VehicleResponseBuilder;
import com.mao.tytmistake.controller.response.page.PageVehicleResponse;
import com.mao.tytmistake.service.GetAllService;
import com.mao.tytmistake.service.VehicleService;
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

@WebMvcTest(VehicleController.class)
class VehicleControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/vehicle";

    @MockBean
    private VehicleService vehicleService;
    @MockBean
    private GetAllService getAllService;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void getAllVehicle_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        PageVehicleRequest testRequest = new PageVehicleRequest();
        PageVehicleResponse testResponse = new PageVehicleResponseBuilder().build();
        Page<PageVehicleResponse> testPage = new PageImpl<>(List.of(testResponse));

        Mockito.when(getAllService.getAllVehicle(Mockito.any(HttpHeaders.class), Mockito.any(PageVehicleRequest.class)))
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
                .getAllVehicle(Mockito.any(HttpHeaders.class), Mockito.any(PageVehicleRequest.class));
    }

    @Test
    void addNewVehicle_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        VehicleRequest testRequest = new VehicleRequestBuilder().build();
        VehicleResponse testResponse = new VehicleResponseBuilder().build();

        Mockito.when(vehicleService.addNewVehicle(Mockito.any(HttpHeaders.class), Mockito.any(VehicleRequest.class)))
                .thenReturn(testResponse);

        mockMvc.perform(post(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(vehicleService, Mockito.times(1))
                .addNewVehicle(Mockito.any(HttpHeaders.class), Mockito.any(VehicleRequest.class));
    }

    @Test
    void updateVehicle_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        Long testVehicleId = 57L;
        VehicleRequest testRequest = new VehicleRequestBuilder().build();
        VehicleResponse testResponse = new VehicleResponseBuilder().build();

        Mockito.when(vehicleService.updateVehicle(Mockito.any(HttpHeaders.class), Mockito.anyLong(), Mockito.any(VehicleRequest.class)))
                .thenReturn(testResponse);

        mockMvc.perform(put(COMMON_PATH + "/{id}", testVehicleId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(vehicleService, Mockito.times(1))
                .updateVehicle(Mockito.any(HttpHeaders.class), Mockito.anyLong(), Mockito.any(VehicleRequest.class));
    }

    @Test
    void removeVehicle_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        Long testVehicleId = 57L;

        Mockito.when(vehicleService.removeVehicle(Mockito.any(HttpHeaders.class), Mockito.anyLong()))
                .thenReturn(testVehicleId);

        mockMvc.perform(delete(COMMON_PATH + "/{id}", testVehicleId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(testVehicleId))
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        Mockito.verify(vehicleService, Mockito.times(1))
                .removeVehicle(Mockito.any(HttpHeaders.class), Mockito.anyLong());
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}