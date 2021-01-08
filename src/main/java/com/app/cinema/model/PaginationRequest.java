package com.app.cinema.model;

import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Data
public class PaginationRequest {

    private Integer pageNumber;
    private Integer pageSize;
    private String sortBy;
    private String sortOrder;
    private String searchQuery;

    public Pageable getPageable() {
        Pageable pageable;
        if (this.getSortOrder().matches("asc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).ascending());
        } else if(this.getSortOrder().matches("desc")) {
            pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortBy).descending());
        } else {
            pageable = PageRequest.of(pageNumber, pageSize);
        }
        return pageable;
    }
}
