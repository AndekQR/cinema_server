package com.app.cinema.service.interfaces;

import com.app.cinema.helper.ChairReservedException;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Reservation;

import java.math.BigDecimal;
import java.util.List;

public interface ReservationService {
    Reservation createReservation(String userEmail, List<Long> chairIds, Long moveId, BigDecimal price) throws NotFoundInDB, ChairReservedException;
}
