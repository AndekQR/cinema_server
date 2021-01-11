package com.app.cinema.dto;

import lombok.Data;

import java.util.Set;

@Data
public class MovieDto {
    private Long id;
    private String startTime;
    private String title;
    private Integer year;
    private Integer runTimeMin;
    private String director;
    private String actors;
    private String plot;
    private String posteUrl;
    private Set<GenreDto> genres;
}
