package com.mao.tytconduct.controller.endpoint;

import com.mao.tytconduct.controller.response.BaseResponse;
import com.mao.tytconduct.controller.response.PageTerminalResponse;
import com.mao.tytconduct.service.TerminalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/terminal")
@RequiredArgsConstructor
public class TerminalController {

    private final TerminalService terminalService;

    @GetMapping
    BaseResponse<PageTerminalResponse> getAllTerminal(@RequestHeader HttpHeaders headers) {

        PageTerminalResponse response = terminalService.getTerminals(headers);
        return BaseResponse.isSuccess(response);
    }
}
