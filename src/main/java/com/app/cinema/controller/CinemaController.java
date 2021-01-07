package com.app.cinema.controller;

import com.app.cinema.Entity.Reservation;
import com.app.cinema.dto.ReservationDto;
import com.app.cinema.helper.ChairReservedException;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.model.ReservationRequest;
import com.app.cinema.service.interfaces.ReservationService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@AllArgsConstructor
public class CinemaController {

    private final ReservationService reservationService;
    private final ModelMapper modelMapper;

    @PostMapping("/makeReservation")
    public ResponseEntity<ReservationDto> makeReservation(@AuthenticationPrincipal UserDetails userDetails, @NonNull @RequestBody ReservationRequest reservationRequest) throws NotFoundInDB, ChairReservedException {
        Reservation reservation=reservationService.createReservation(userDetails.getUsername(),
                reservationRequest.getChairIds(),
                reservationRequest.getMoveId(),
                BigDecimal.valueOf(reservationRequest.getPrice()));
        return new ResponseEntity<>(convertToDto(reservation), HttpStatus.CREATED);
    }

    private ReservationDto convertToDto(Reservation reservation) {
        return this.modelMapper.map(reservation, ReservationDto.class);
    }


}
