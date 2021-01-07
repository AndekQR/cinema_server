package com.app.cinema.dto;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class MovieDto {
    private Long id;
    private LocalDateTime startTime;
    private String title;
    private Integer year;
    private Integer runTimeMin;
    private String director;
    private String actors;
    private String plot;
    private String posteUrl;
    private Set<GenreDto> genres;
}
