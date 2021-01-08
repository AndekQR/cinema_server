package com.app.cinema.controller;

import com.app.cinema.Entity.Movie;
import com.app.cinema.Entity.Reservation;
import com.app.cinema.dto.MovieDto;
import com.app.cinema.dto.ReservationDto;
import com.app.cinema.helper.ChairReservedException;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.model.PaginationRequest;
import com.app.cinema.model.PaginationResponse;
import com.app.cinema.model.ReservationRequest;
import com.app.cinema.service.interfaces.MovieService;
import com.app.cinema.service.interfaces.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@AllArgsConstructor
public class CinemaController {

    private final ReservationService reservationService;
    private final Mapper mapper;
    private final MovieService movieService;

    @PostMapping("/makeReservation")
    public ResponseEntity<ReservationDto> makeReservation(@AuthenticationPrincipal UserDetails userDetails, @NonNull @RequestBody ReservationRequest reservationRequest) throws NotFoundInDB, ChairReservedException {
        Reservation reservation=reservationService.createReservation(userDetails.getUsername(),
                reservationRequest.getChairIds(),
                reservationRequest.getMoveId(),
                BigDecimal.valueOf(reservationRequest.getPrice()));
        return new ResponseEntity<>(mapper.mapObject(reservation, ReservationDto.class), HttpStatus.CREATED);
    }


    @GetMapping("/movies")
    public ResponseEntity<PaginationResponse<MovieDto>> getMovies(PaginationRequest paginationRequest) {
        Page<Movie> moviesPage=this.movieService.getMoviesPage(paginationRequest);
        PaginationResponse<MovieDto> paginationResponse = PaginationResponse.<MovieDto>builder()
                .page(moviesPage.getNumber())
                .content(mapper.mapList(moviesPage.getContent(), MovieDto.class))
                .size(moviesPage.getSize())
                .sortMethod(paginationRequest.getSortOrder())
                .totalRows(moviesPage.getTotalElements())
                .sortedBy(paginationRequest.getSortBy())
                .build();
        return ResponseEntity.ok(paginationResponse);
    }


}
