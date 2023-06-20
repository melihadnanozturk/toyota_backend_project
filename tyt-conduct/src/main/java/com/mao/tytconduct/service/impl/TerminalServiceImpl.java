package com.mao.tytconduct.service.impl;

import com.mao.tytconduct.client.AuthApiClient;
import com.mao.tytconduct.controller.response.PageTerminalResponse;
import com.mao.tytconduct.controller.response.TerminalResponse;
import com.mao.tytconduct.model.entity.TerminalEntity;
import com.mao.tytconduct.repository.TerminalEntityRepository;
import com.mao.tytconduct.service.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TerminalServiceImpl implements TerminalService {

    private final TerminalEntityRepository terminalEntityRepository;
    private final AuthApiClient apiClient;

    @Override
    public PageTerminalResponse getTerminals(HttpHeaders headers) {
        apiClient.auth(headers);
        List<TerminalEntity> terminalEntities = terminalEntityRepository.findAll();

        List<TerminalResponse> terminals = terminalEntities.stream().map(TerminalResponse::mappedEntityToResponse).toList();
        return PageTerminalResponse.builder().terminalResponseList(terminals).build();
    }
}
