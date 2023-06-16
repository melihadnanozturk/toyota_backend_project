package com.mao.tytmistake.controller.request.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.ASC;

@Getter
@Setter
public class TytPageRequest {

    @Builder.Default
    private Integer pageNumber = 0;
    @Builder.Default
    private Integer pageSize = 20;
    @Builder.Default
    private String sortOf = ASC.toString();
    protected TytSortCol sortCol;

    public static Pageable createPageRequest(TytPageRequest request) {
        return PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortCol().getColon());
    }
}
