package com.app.cinema.service.interfaces;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Genre;
import com.app.cinema.Entity.Movie;
import com.app.cinema.model.PaginationRequest;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Set;

public interface MovieService {
    Set<Genre> saveGenres(List<String> genreNames);
    Movie getMovieById(Long id) throws NotFoundInDB;
    List<Movie> getMoviesPage(PaginationRequest paginationRequest);
    List<Movie> getAllMovies();
}
