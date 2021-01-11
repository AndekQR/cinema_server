package com.app.cinema.repository;

import com.app.cinema.Entity.Chair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChairRepository extends JpaRepository<Chair, Long> {
    List<Chair> findByCinemaHallId(Long cinemaHallId);
}
