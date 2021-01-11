package com.app.cinema.dto;

import com.app.cinema.Entity.Cinema;
import lombok.Data;

@Data
public class CinemaHallDto {
    private Long id;
    private String name;
    private Float screenSizeInch;
    private CinemaDto cinemaBuilding;
}
