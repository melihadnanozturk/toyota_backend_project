package com.mao.tytconduct.controller.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * This class is used to response terminal information in page.
 * This class contains TerminalResponses
 * The class uses the Lombok annotations @Setter,  @Getter and @Builder to automatically generate getters and a builder method.
 */
@Getter
@Setter
@Builder
public class PageTerminalResponse {

    private List<TerminalResponse> terminalResponseList;
}
