package com.mao.tytmistake.controller.request.page;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static org.springframework.data.domain.Sort.Direction.ASC;

/**
 * Represents a page request for pagination with TYT(Toyota-Backend)-specific sorting.
 */
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

    /**
     * Creates a Pageable object based on the TytPageRequest.
     *
     * @param request the TytPageRequest containing the pagination and sorting information
     * @return a Pageable object for pagination and sorting
     */
    public static Pageable createPageRequest(TytPageRequest request) {
        return PageRequest.of(
                request.getPageNumber(),
                request.getPageSize(),
                Sort.Direction.valueOf(request.getSortOf()),
                request.getSortCol().getColon());
    }
}
