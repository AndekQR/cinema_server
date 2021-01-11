package com.app.cinema.repository;

import com.app.cinema.Entity.CinemaHall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CinemaHallRepository extends JpaRepository<CinemaHall, Long> {
    List<CinemaHall> findAllByCinemaBuildingId(Long cinemaId);
}
