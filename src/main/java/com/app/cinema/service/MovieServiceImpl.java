package com.app.cinema.service;

import com.app.cinema.helper.EntitySpecification;
import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Genre;
import com.app.cinema.Entity.Movie;
import com.app.cinema.model.PaginationRequest;
import com.app.cinema.repository.GenreRepository;
import com.app.cinema.repository.MovieRepository;
import com.app.cinema.service.interfaces.MovieService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;
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

    @Override
    public Page<Movie> getMoviesPage(PaginationRequest paginationRequest) {
        Page<Movie> all=this.movieRepository.findAll(Specification.where(
                EntitySpecification.textAtLeastInOneColumn(paginationRequest.getSearchQuery())),
                paginationRequest.getPageable()
        );
        return all;
    }

    @Override
    public List<Genre> getAllGenres() {
        return this.genreRepository.findAll();
    }

    @Override
    public List<Movie> findMoviesByGenresName(List<String> names) {
        Set<Genre> genreList = new HashSet<>();
        names.forEach(name -> {
            Optional<Genre> byName=this.genreRepository.findByName(name);
            byName.ifPresent(genreList::add);
        });
        return movieRepository.findAllByGenres(genreList);
    }


}
