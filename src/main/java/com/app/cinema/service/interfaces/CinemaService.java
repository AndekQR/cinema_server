package com.app.cinema.service.interfaces;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Cinema;
import com.app.cinema.Entity.CinemaHall;

public interface CinemaService {

    CinemaHall findCinemaHallById(Long id) throws NotFoundInDB;
    Cinema findCinemaById(Long id) throws NotFoundInDB;
}
