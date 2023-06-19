package com.mao.tytconduct.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.tytconduct.BaseControllerTests;
import com.mao.tytconduct.controller.UserRequestBuilder;
import com.mao.tytconduct.controller.request.UserRequest;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.controller.response.UserResponseBuilder;
import com.mao.tytconduct.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/user";

    @MockBean
    private UserService userService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void addNewUser_happyPath() throws Exception {
        HttpHeaders testHeaders = new HttpHeaders();
        UserRequest testRequest = new UserRequestBuilder().build();
        UserResponse testResponse = new UserResponseBuilder().build();

        Mockito.when(userService.addNewUser(any(HttpHeaders.class), any(UserRequest.class))).thenReturn(testResponse);

        mockMvc.perform(post(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value(testResponse.getName()))
                .andExpect(jsonPath("$.response.password").value(testResponse.getPassword()))
                .andExpect(jsonPath("$.response.roles").isNotEmpty());
    }

    @Test
    void updateUser_happyPat() throws Exception {
        Long testId = 1L;
        HttpHeaders testHeaders = new HttpHeaders();
        UserRequest testRequest = new UserRequestBuilder().build();
        UserResponse testResponse = new UserResponseBuilder()
                .build();

        Mockito.when(userService.updateUser(any(HttpHeaders.class), anyLong(), any(UserRequest.class))).thenReturn(testResponse);

        mockMvc.perform(put(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value(testResponse.getName()))
                .andExpect(jsonPath("$.response.password").value(testResponse.getPassword()))
                .andExpect(jsonPath("$.response.roles").isNotEmpty());
    }

    @Test
    void deleteUser_happyPath() throws Exception {
        Long testId = 1L;
        HttpHeaders testHeaders = new HttpHeaders();

        Mockito.when(userService.removeUser(any(HttpHeaders.class), anyLong())).thenReturn(testId);

        mockMvc.perform(delete(COMMON_PATH + "/{id}", testId)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value(testId));
    }
}