package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.response.PageTerminalResponse;
import org.springframework.http.HttpHeaders;

public interface TerminalService {

    PageTerminalResponse getTerminals(HttpHeaders headers);
}
