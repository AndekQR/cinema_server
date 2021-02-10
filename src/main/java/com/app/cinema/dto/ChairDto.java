package com.app.cinema.dto;

import com.app.cinema.helper.ChairType;
import lombok.Data;

@Data
public class ChairDto {
    private Long id;
    private ChairType type;
    private Integer number;
    private CinemaHallDto cinemaHall;
}
