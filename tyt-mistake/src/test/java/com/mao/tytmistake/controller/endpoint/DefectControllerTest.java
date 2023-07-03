package com.mao.tytmistake.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.tytmistake.BaseControllerTests;
import com.mao.tytmistake.controller.request.DefectRequest;
import com.mao.tytmistake.controller.request.UpdateDefectRequest;
import com.mao.tytmistake.controller.request.UpdateVehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.request.VehicleDefectRequestBuilder;
import com.mao.tytmistake.controller.request.page.PageDefectRequest;
import com.mao.tytmistake.controller.response.DefectResponse;
import com.mao.tytmistake.controller.response.PageDefectResponse;
import com.mao.tytmistake.controller.response.PageVehicleDefectResponseBuilder;
import com.mao.tytmistake.controller.response.VehicleDefectResponseBuilder;
import com.mao.tytmistake.service.DefectService;
import com.mao.tytmistake.service.GetAllService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(DefectController.class)
class DefectControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/vd";

    @MockBean
    private DefectService defectService;
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
        PageDefectResponse testResponse = new PageVehicleDefectResponseBuilder().build();
        Page<PageDefectResponse> testPage = new PageImpl<>(List.of(testResponse));

        when(getAllService.getAllDefect(any(HttpHeaders.class), any(PageDefectRequest.class)))
                .thenReturn(testPage);

        mockMvc.perform(get(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(getAllService, times(1))
                .getAllDefect(any(HttpHeaders.class), any(PageDefectRequest.class));
    }

    @Test
    void addNewDefect_happyPath() throws Exception {
        HttpHeaders testHeaders = createHeader();
        DefectRequest testRequest = new VehicleDefectRequestBuilder().build();
        DefectResponse testResponse = new VehicleDefectResponseBuilder().build();

        when(defectService.addNewDefect(any(HttpHeaders.class), any(DefectRequest.class)))
                .thenReturn(testResponse);

        mockMvc.perform(post(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(defectService, times(1))
                .addNewDefect(any(HttpHeaders.class), any(DefectRequest.class));
    }

    @Test
    void addNewDefectImage_happyPath() throws Exception {
        MockMultipartFile imageFile = new MockMultipartFile("imageFile",
                "test.jpg", MediaType.IMAGE_JPEG_VALUE,
                "Test Image".getBytes());
        Long testId = 123L;
        HttpHeaders headers = createHeader();

        when(defectService.addDefectImage(any(HttpHeaders.class), any(MultipartFile.class), anyLong()))
                .thenReturn(testId);

        mockMvc.perform(MockMvcRequestBuilders.multipart(COMMON_PATH + "/{id}", testId)
                        .file(imageFile)
                        .headers(headers))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(testId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localDateTime").isNotEmpty());

        verify(defectService, times(1))
                .addDefectImage(any(HttpHeaders.class), any(MultipartFile.class), anyLong());
    }

    @Test
    void getDefectImage_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        String testName = "test";

        when(getAllService.getDefectImage(any(HttpHeaders.class), anyLong()))
                .thenReturn(testName.getBytes());

        mockMvc.perform(get(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.IMAGE_JPEG))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().bytes(testName.getBytes()));

        verify(getAllService, times(1))
                .getDefectImage(any(HttpHeaders.class), anyLong());
    }

    @Test
    void updateDefect_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();
        UpdateDefectRequest testRequest = new UpdateVehicleDefectRequestBuilder().build();
        DefectResponse testResponse = new VehicleDefectResponseBuilder().build();

        when(defectService.updateDefect(any(HttpHeaders.class), any(UpdateDefectRequest.class), anyLong()))
                .thenReturn(testResponse);

        mockMvc.perform(put(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(defectService, times(1))
                .updateDefect(any(HttpHeaders.class), any(UpdateDefectRequest.class), anyLong());
    }

    @Test
    void testUpdateDefectImage() throws Exception {
        Long defectId = 123L;

        MockMultipartFile file = new MockMultipartFile(
                "imageFile",
                "test.jpg",
                "image/jpeg",
                "test image".getBytes()
        );

        when(defectService.updateDefectImage(any(HttpHeaders.class), any(MultipartFile.class), any(Long.class)))
                .thenReturn(defectId);

        MockMultipartHttpServletRequestBuilder builder =
                MockMvcRequestBuilders.multipart(COMMON_PATH + "/img/{defectId}", defectId);

        builder.with(request -> {
            request.setMethod("PATCH");
            return request;
        });

        mockMvc.perform(builder
                        .file(file)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.response").value(defectId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.localDateTime").isNotEmpty());

        verify(defectService).updateDefectImage(any(HttpHeaders.class), any(MultipartFile.class), eq(defectId));
    }

    @Test
    void deleteDefect_happyPath() throws Exception {
        Long testId = 57L;
        HttpHeaders testHeaders = createHeader();

        when(defectService.deleteDefect(any(HttpHeaders.class), anyLong()))
                .thenReturn(testId);

        mockMvc.perform(delete(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(testId))
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(defectService, times(1))
                .deleteDefect(any(HttpHeaders.class), anyLong());
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}