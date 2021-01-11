package com.app.cinema.service;

import com.app.cinema.Entity.Cinema;
import com.app.cinema.Entity.CinemaHall;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Chair;
import com.app.cinema.Entity.Reservation;
import com.app.cinema.repository.ChairRepository;
import com.app.cinema.service.interfaces.ChairService;
import com.app.cinema.service.interfaces.CinemaService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChairServiceImpl implements ChairService {

    private final ChairRepository chairRepository;
    private final CinemaService cinemaService;

    @Override
    public Chair findChair(Long chairId) throws NotFoundInDB {
        Optional<Chair> optionalChair=chairRepository.findById(chairId);
        if (optionalChair.isPresent()) return optionalChair.get();
        else throw new NotFoundInDB("Chair id " + chairId + " not exist");
    }

    @Override
    public Boolean isReserved(Long chairId, Long movieId) throws NotFoundInDB {
        Chair chair=this.findChair(chairId);
        List<Reservation> reservations=chair.getReservations();
        for (Reservation reservation : reservations) {
            Long movieIdToCheck=reservation.getMovie().getId();
            if (movieIdToCheck.equals(movieId)) return true;
        }
        return false;

    }

    @Override
    public List<Chair> findChairsByCinemaHallId(Long cinemaHallId) {
        return chairRepository.findByCinemaHallId(cinemaHallId);
    }

    @Override
    public List<Chair> findChairsByCinemaId(Long cinemaId) throws NotFoundInDB {
        Cinema cinema=cinemaService.findCinemaById(cinemaId);
        List<Chair> result = new ArrayList<>();
        cinema.getCinemaHalls().forEach(hall -> {
            result.addAll(hall.getChairs());
        });
        return result;
    }
}
