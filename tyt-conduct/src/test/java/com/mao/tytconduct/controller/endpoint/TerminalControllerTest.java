package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.BaseControllerTests;
import com.mao.tytconduct.controller.response.PageTerminalResponse;
import com.mao.tytconduct.controller.response.PageTerminalResponseBuilder;
import com.mao.tytconduct.service.TerminalService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TerminalController.class)
class TerminalControllerTest extends BaseControllerTests {

    @MockBean
    private TerminalService terminalService;

    private static final String COMMON_PATH = "/terminal";

    @Test
    void getAllTerminal() throws Exception {
        HttpHeaders testHeaders = createHeader();
        PageTerminalResponse testResponse = new PageTerminalResponseBuilder().build();

        when(terminalService.getTerminals(Mockito.any(HttpHeaders.class))).thenReturn(testResponse);

        mockMvc.perform(get(COMMON_PATH)
                        .headers(testHeaders)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").isNotEmpty())
                .andExpect(jsonPath("$.localDateTime").isNotEmpty());

        verify(terminalService, times(1))
                .getTerminals(any(HttpHeaders.class));

    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}