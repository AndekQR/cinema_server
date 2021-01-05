package com.app.cinema.service;

import com.app.cinema.model.Genre;
import com.app.cinema.repository.GenreRepository;
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
}
