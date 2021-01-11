package com.app.cinema.service.interfaces;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Cinema;
import com.app.cinema.Entity.CinemaHall;

import java.util.List;

public interface CinemaService {

    CinemaHall findCinemaHallById(Long id) throws NotFoundInDB;
    Cinema findCinemaById(Long id) throws NotFoundInDB;
    List<Cinema> getAllCinemas();
    List<CinemaHall> findCinemaHallsByCinemaId(Long cinemaId);
}
