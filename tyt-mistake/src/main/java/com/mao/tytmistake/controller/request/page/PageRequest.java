package com.mao.tytmistake.controller.request.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Getter
@Setter
public class PageRequest {

    @Builder.Default
    private Integer pageNumber = 0;
    @Builder.Default
    private Integer pageSize = 20;
    @Builder.Default
    private String sortOf = ASC.toString();
    @Builder.Default
    private String sortBy = "id";
}
