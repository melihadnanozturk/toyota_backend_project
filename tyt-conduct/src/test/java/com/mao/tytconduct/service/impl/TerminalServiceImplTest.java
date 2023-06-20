package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.BaseUnitTest;
import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.controller.response.PageTerminalResponse;
import com.mao.tytconduct.controller.response.TerminalResponse;
import com.mao.tytconduct.model.TerminalEntityBuilder;
import com.mao.tytconduct.model.entity.TerminalEntity;
import com.mao.tytconduct.repository.TerminalEntityRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class TerminalServiceImplTest extends BaseUnitTest {

    @Mock
    private TerminalEntityRepository terminalEntityRepository;
    @Mock
    private AuthApiClient apiClient;

    @InjectMocks
    private TerminalServiceImpl terminalService;

    @Test
    void getTerminals_happyPath() {
        HttpHeaders testHeaders = createHeader();
        TerminalEntity testEntity = new TerminalEntityBuilder().build();
        TerminalResponse expected = TerminalResponse.mappedEntityToResponse(testEntity);

        when(terminalEntityRepository.findAll()).thenReturn(List.of(testEntity));

        PageTerminalResponse response = terminalService.getTerminals(testHeaders);

        Assertions.assertTrue(response.getTerminalResponseList().contains(expected));
        Mockito.verify(terminalEntityRepository, Mockito.times(1)).findAll();
        Mockito.verify(apiClient, Mockito.times(1)).auth(any(HttpHeaders.class));
    }

    private HttpHeaders createHeader() {
        HttpHeaders newHeaders = new HttpHeaders();
        newHeaders.set("userName", "userName");
        newHeaders.set("Authorization", "token");

        return newHeaders;
    }
}