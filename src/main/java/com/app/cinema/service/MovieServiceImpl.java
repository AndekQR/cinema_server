package com.app.cinema.service;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Genre;
import com.app.cinema.Entity.Movie;
import com.app.cinema.repository.GenreRepository;
import com.app.cinema.repository.MovieRepository;
import com.app.cinema.service.interfaces.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final GenreRepository genreRepository;
    private final MovieRepository movieRepository;


    @Override
    public Set<Genre> saveGenres(List<String> genreNames) {
        return genreNames.stream().map(element -> {
            Optional<Genre> optionalGenre = genreRepository.findByName(element);
            if (optionalGenre.isEmpty()) {
                Genre genre = new Genre();
                genre.setName(element);
                genreRepository.save(genre);
                return genre;
            }
            return optionalGenre.get();
        }).collect(Collectors.toSet());
    }

    @Override
    public Movie getMovieById(Long id) throws NotFoundInDB {
        Optional<Movie> optionalMovie=movieRepository.findById(id);
        if (optionalMovie.isPresent()) return optionalMovie.get();
        else throw new NotFoundInDB("Movie id "+id+" not exist");
    }
}
