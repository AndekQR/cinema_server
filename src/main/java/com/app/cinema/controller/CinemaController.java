package com.app.cinema.controller;

import com.app.cinema.Entity.*;
import com.app.cinema.dto.*;
import com.app.cinema.helper.ChairReservedException;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.model.PaginationRequest;
import com.app.cinema.model.PaginationResponse;
import com.app.cinema.model.ReservationRequest;
import com.app.cinema.service.interfaces.ChairService;
import com.app.cinema.service.interfaces.CinemaService;
import com.app.cinema.service.interfaces.MovieService;
import com.app.cinema.service.interfaces.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
public class CinemaController {

    private final ReservationService reservationService;
    private final Mapper mapper;
    private final MovieService movieService;
    private final ChairService chairService;
    private final CinemaService cinemaService;

    @PostMapping("/reservation")
    public ResponseEntity<ReservationDto> makeReservation(@AuthenticationPrincipal UserDetails userDetails,
                                                          @NonNull @RequestBody ReservationRequest reservationRequest)
            throws NotFoundInDB, ChairReservedException {
        Reservation reservation=reservationService.createReservation(userDetails.getUsername(),
                reservationRequest.getChairIds(),
                reservationRequest.getMoveId(),
                BigDecimal.valueOf(reservationRequest.getPrice()));
        return new ResponseEntity<>(mapper.mapObject(reservation, ReservationDto.class), HttpStatus.CREATED);
    }

    @GetMapping("/reservations")
    public ResponseEntity<List<ReservationDto>> getUserReservations(@AuthenticationPrincipal UserDetails userDetails)
            throws UsernameNotFoundException {
        List<Reservation> userReservations=reservationService.getUserReservations(userDetails.getUsername());
        List<ReservationDto> reservationDtos=mapper.mapList(userReservations, ReservationDto.class);
        return ResponseEntity.ok(reservationDtos);
    }

    @GetMapping("reservations/chairs/{cinemaHallId}/{movieId}")
    public ResponseEntity<List<ChairDto>> getReservedChairs(@PathVariable(name="cinemaHallId") Long cinemaHallId,
                                                            @PathVariable(name="movieId") Long movieId) {

        List<Reservation> byMovieAndCinemaHall=this.reservationService.findByMovieAndCinemaHall(movieId, cinemaHallId);
        List<Chair> listOfChairs=byMovieAndCinemaHall.stream()
                .flatMap(element -> element.getChairs().stream())
                .collect(Collectors.toList());
        List<ChairDto> chairDtos=this.mapper.mapList(listOfChairs, ChairDto.class);
        return ResponseEntity.ok(chairDtos);
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

    @GetMapping("/cinemaHall/{id}/chairs")
    public ResponseEntity<List<ChairDto>> getChairsByCinemaHall(@NotNull @PathVariable(name="id") Long cinemaHallId) {
        List<Chair> chairsByCinemaHallId=chairService.findChairsByCinemaHallId(cinemaHallId);
        return ResponseEntity.ok(mapper.mapList(chairsByCinemaHallId, ChairDto.class));
    }

    @GetMapping("/cinema/{id}/chairs")
    public ResponseEntity<List<ChairDto>> getChairsByCinema(@NotNull @PathVariable(name="id") Long cinemaId) throws NotFoundInDB {
        List<Chair> chairsByCinemaId=chairService.findChairsByCinemaId(cinemaId);
        return ResponseEntity.ok(mapper.mapList(chairsByCinemaId, ChairDto.class));
    }

    @GetMapping("/cinema")
    public ResponseEntity<List<CinemaDto>> getCinemas() {
        List<Cinema> allCinemas=this.cinemaService.getAllCinemas();
        List<CinemaDto> mapped=mapper.mapList(allCinemas, CinemaDto.class);
        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/cinema/{id}")
    public ResponseEntity<CinemaDto> getCinema(@NotNull @PathVariable(name="id") Long cinemaId) throws NotFoundInDB {
        Cinema cinemaById=this.cinemaService.findCinemaById(cinemaId);
        CinemaDto cinemaDto=mapper.mapObject(cinemaById, CinemaDto.class);
        return ResponseEntity.ok(cinemaDto);
    }

    @GetMapping("/cinema/{id}/cinemaHall")
    public ResponseEntity<List<CinemaHallDto>> getCinemaHallsByCinema(@NotNull @PathVariable(name="id") Long cinemaId) {
        List<CinemaHall> cinemaHallsByCinemaId=cinemaService.findCinemaHallsByCinemaId(cinemaId);
        List<CinemaHallDto> mapped=mapper.mapList(cinemaHallsByCinemaId, CinemaHallDto.class);
        return ResponseEntity.ok(mapped);
    }

    @GetMapping("/cinema/cinemaHall/{id}")
    public ResponseEntity<CinemaHallDto> getCinemaHall(@NotNull @PathVariable(name="id") Long cinemaHallId) throws NotFoundInDB {
        CinemaHall cinemaHallById=this.cinemaService.findCinemaHallById(cinemaHallId);
        CinemaHallDto cinemaHallDto=mapper.mapObject(cinemaHallById, CinemaHallDto.class);
        return ResponseEntity.ok(cinemaHallDto);
    }

    @GetMapping("/genres")
    public ResponseEntity<List<GenreDto>> getAllGenres() {
        List<Genre> allGenres=this.movieService.getAllGenres();
        List<GenreDto> genreDtos=mapper.mapList(allGenres, GenreDto.class);
        return ResponseEntity.ok(genreDtos);
    }

    @GetMapping("/movies/{genre}")
    public ResponseEntity<PaginationResponse<MovieDto>> getMoviesByGenre(@NotNull @PathVariable(name="genre") List<String> genres,
                                                                         PaginationRequest paginationRequest) {
        Page<Movie> moviesByGenresName=movieService.findMoviesByGenresName(genres, paginationRequest);
                PaginationResponse<MovieDto> paginationResponse = PaginationResponse.<MovieDto>builder()
                .page(moviesByGenresName.getNumber())
                .content(mapper.mapList(moviesByGenresName.getContent(), MovieDto.class))
                .size(moviesByGenresName.getSize())
                .sortMethod(paginationRequest.getSortOrder())
                .totalRows(moviesByGenresName.getTotalElements())
                .sortedBy(paginationRequest.getSortBy())
                .build();
        return ResponseEntity.ok(paginationResponse);
    }

    @GetMapping("/movies/recommended")
    public ResponseEntity<PaginationResponse<MovieDto>> getRecommendedMoviesForUser(@AuthenticationPrincipal UserDetails userDetails, PaginationRequest paginationRequest) throws UsernameNotFoundException {
        // 1. Pobierz historyczne rezerwacje użytkownika
        List<Reservation> userReservations=reservationService.getUserReservations(userDetails.getUsername());
        // 2. Zbierz wszystkie gatunki (powtarzające się) TODO ładniej to napisać
        // 3. Mapuj gatunek (nazwa gatunku dla uproszczenia dalej) - liczba wystąpień
        Map<String, Integer> occurences = new HashMap<>();
        Set<Long> watchedMovieIds = new HashSet<>();
        for (Reservation res: userReservations) {
            // Do usuwania obejrzanych filmów później
            watchedMovieIds.add(res.getMovie().getId());
            for (Genre genre: res.getMovie().getGenres()) {
                Integer count = occurences.putIfAbsent(genre.getName(), 1);
                if (count != null) {
                    occurences.replace(genre.getName(), count+1);
                }
            }
        }
        // 4. Wyszukaj obecnie występujące filmy z ulubionego gatunku
        // to jest lista dla czytelności, żeby mieć jbc możliwość uwzględnić więcej niż dwa gatunki
        List<String> favoriteGenres = occurences.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        System.out.println(favoriteGenres);
        // 5. Wybierz filmy z danego gatunku zaczynając od ulubionego (najwyższy count w rezerwacjach)
        // Ignoruj datę startu filmu jak w pozostałych funkcjach dla zgodności
        for (String genre: favoriteGenres) {
            // Zwróć tylko to gdzie znaleziono filmy (jakbym włączał wybierania po dacie),
            // bez tego znajdzie raczej zawsze
            Page<Movie> moviesByGenresName=movieService.findUnwatchedMoviesByGenreNames(
                    Collections.singletonList(genre),
                    watchedMovieIds,
                    paginationRequest);
            if (!moviesByGenresName.isEmpty()) {
                System.out.println("Znaleziono filmy z ulubionego gatunku: "+genre);
//                List<MovieDto> movieDtos=mapper.mapList(moviesByGenresName, MovieDto.class);
                PaginationResponse<MovieDto> paginationResponse = PaginationResponse.<MovieDto>builder()
                        .page(moviesByGenresName.getNumber())
                        .content(mapper.mapList(moviesByGenresName.getContent(), MovieDto.class))
                        .size(moviesByGenresName.getSize())
                        .sortMethod(paginationRequest.getSortOrder())
                        .totalRows(moviesByGenresName.getTotalElements())
                        .sortedBy(paginationRequest.getSortBy())
                        .build();
                return ResponseEntity.ok(paginationResponse);
            }
        }
        return ResponseEntity.ok(PaginationResponse.<MovieDto>builder().build());
    }
}
