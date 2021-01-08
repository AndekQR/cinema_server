package com.app.cinema.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaginationResponse<T> {
    private Long totalRows;
    private List<T> content;
    private Integer page;
    private Integer size;
    private String sortMethod;
    private String sortedBy;
}
