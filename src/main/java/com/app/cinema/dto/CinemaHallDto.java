package com.app.cinema.dto;

import lombok.Data;

import java.util.List;

@Data
public class CinemaHallDto {
    private Long id;
    private String name;
    private Float screenSizeInch;
    private List<ChairDto> chairs;
}
