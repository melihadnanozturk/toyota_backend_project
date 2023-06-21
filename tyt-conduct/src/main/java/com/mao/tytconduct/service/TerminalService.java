package com.mao.tytconduct.service;

import com.mao.tytconduct.controller.response.PageTerminalResponse;
import org.springframework.http.HttpHeaders;

/**
 * Service interface for terminal operations.
 */
public interface TerminalService {

    /**
     * This method checks if client is valid retrieves terminal information.
     *
     * @param headers - Username, Password
     */
    PageTerminalResponse getTerminals(HttpHeaders headers);
}
