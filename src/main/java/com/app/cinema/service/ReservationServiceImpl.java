package com.app.cinema.service;

import com.app.cinema.Entity.*;
import com.app.cinema.helper.ChairReservedException;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.repository.ReservationRepository;
import com.app.cinema.service.interfaces.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ReservationServiceImpl implements ReservationService {


    private final ReservationRepository reservationRepository;

    private final ChairService chairService;
    private final UserService userService;
    private final MovieService movieService;

    @Override
    public Reservation createReservation(String userEmail, List<Long> chairIds, Long moveId, BigDecimal price) throws NotFoundInDB, ChairReservedException {
        List<Chair> chairsToReserve=new ArrayList<>();
        for (Long chairId : chairIds) {
            if (chairService.isReserved(chairId, moveId)) {
                throw new ChairReservedException(chairId);
            } else {
                //jezeli fotel nie istnieje metoda isReserved wyrzuci NotFoundInDB
                Chair chair=chairService.findChair(chairId);
                chairsToReserve.add(chair);
            }
        }
        User user=userService.findByEmail(userEmail);
        Movie movie=movieService.getMovieById(moveId);

        Reservation reservation=new Reservation();
        reservation.setChairs(chairsToReserve);
        reservation.setClient(user);
        reservation.setMovie(movie);
        reservation.setCinemaHall(chairsToReserve.get(0).getCinemaHall());
        reservation.setPrice(price);

        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getUserReservations(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return user.getReservations();
    }
}
