package com.mao.tytauth.controller.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mao.tytauth.BaseControllerTests;
import com.mao.tytauth.model.Role;
import com.mao.tytauth.service.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/auth";

    @MockBean
    private TokenService tokenService;

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        objectMapper = new ObjectMapper();
    }

    @Test
    void auth_happyPath() throws Exception {
        HttpHeaders testHeaders = new HttpHeaders();
        when(tokenService.authentication(any(HttpHeaders.class))).thenReturn(true);

        mockMvc.perform(post(COMMON_PATH + "/validate-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(testHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(tokenService, times(1))
                .authentication(any(HttpHeaders.class));
    }

    @Test
    void login_happyPath() throws Exception {
        String token = "Bearer token";
        HttpHeaders testHeaders = new HttpHeaders();
        testHeaders.set("userName", "testUser");
        testHeaders.set("password", "testPassword");
        when(tokenService.createToken(any(HttpHeaders.class))).thenReturn(token);

        mockMvc.perform(post(COMMON_PATH + "/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .headers(testHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(tokenService, times(1))
                .createToken(any(HttpHeaders.class));
    }

    @Test
    void validate_happyPath() throws Exception {
        HttpHeaders testHeaders = new HttpHeaders();
        testHeaders.set("userName", "testUser");
        testHeaders.set("Authorization", "Bearer token");
        when(tokenService.authorization(any(HttpHeaders.class), any(Role.class))).thenReturn(true);

        mockMvc.perform(post(COMMON_PATH + "/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(Role.ADMIN))
                        .headers(testHeaders))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(tokenService, times(1))
                .authorization(any(HttpHeaders.class), any(Role.class));
    }
}