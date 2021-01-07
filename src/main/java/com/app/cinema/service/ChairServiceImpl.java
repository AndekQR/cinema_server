package com.app.cinema.service;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Chair;
import com.app.cinema.Entity.Reservation;
import com.app.cinema.repository.ChairRepository;
import com.app.cinema.service.interfaces.ChairService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class ChairServiceImpl implements ChairService {

    private final ChairRepository chairRepository;

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
}
