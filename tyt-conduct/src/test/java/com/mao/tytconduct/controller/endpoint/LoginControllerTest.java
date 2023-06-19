package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.BaseControllerTests;
import com.mao.tytconduct.controller.response.UserResponse;
import com.mao.tytconduct.controller.response.UserResponseBuilder;
import com.mao.tytconduct.service.LoginService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LoginController.class)
class LoginControllerTest extends BaseControllerTests {

    private static final String COMMON_PATH = "/check";

    @MockBean
    private LoginService loginService;

    @Test
    void checkUser_happyPath() throws Exception {
        String testUserName = "testName";
        String testPassword = "testPassword";
        UserResponse testResponse = new UserResponseBuilder()
                .withName(testUserName)
                .withPassword(testPassword)
                .build();

        Mockito.when(loginService.isUserValid(any(HttpHeaders.class))).thenReturn(testResponse);

        mockMvc.perform(post(COMMON_PATH)
                        .header("userName", testUserName)
                        .header("password", testPassword)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response.name").value(testResponse.getName()))
                .andExpect(jsonPath("$.response.password").value(testResponse.getPassword()))
                .andExpect(jsonPath("$.response.roles").isNotEmpty());
    }
}