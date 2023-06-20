package com.mao.tytconduct.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PageTerminalResponse {

    private List<TerminalResponse> terminalResponseList;
}
