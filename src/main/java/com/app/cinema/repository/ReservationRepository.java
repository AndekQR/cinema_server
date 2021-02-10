package com.app.cinema.repository;

import com.app.cinema.Entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByMovieIdAndCinemaHallId(Long movie_id, Long cinemaHall_id);
}
