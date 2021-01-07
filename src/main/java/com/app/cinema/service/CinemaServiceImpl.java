package com.app.cinema.service;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Cinema;
import com.app.cinema.Entity.CinemaHall;
import com.app.cinema.repository.CinemaHallRepository;
import com.app.cinema.repository.CinemaRepository;
import com.app.cinema.service.interfaces.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaHallRepository cinemaHallRepository;

    @Override
    public CinemaHall findCinemaHallById(Long id) throws NotFoundInDB {
        Optional<CinemaHall> optionalCinemaHall=cinemaHallRepository.findById(id);
        if (optionalCinemaHall.isPresent()) return optionalCinemaHall.get();
        else throw new NotFoundInDB("Cinema hall id "+id+ " not exist");
    }

    @Override
    public Cinema findCinemaById(Long id) throws NotFoundInDB {
        Optional<Cinema> optionalCinema=cinemaRepository.findById(id);
        if (optionalCinema.isPresent()) return optionalCinema.get();
        else throw new NotFoundInDB("Cinema id "+id+ " not exist");
    }
}
