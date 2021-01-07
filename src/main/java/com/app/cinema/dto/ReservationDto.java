package com.app.cinema.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ReservationDto {

    private Long id;
    private UserDto client;
    private MovieDto movie;
    private CinemaHallDto cinemaHall;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private BigDecimal price;
    private List<ChairDto> chairs;
}
