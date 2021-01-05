package com.app.cinema.service.interfaces;

import com.app.cinema.model.Genre;

import java.util.List;
import java.util.Set;

public interface MovieService {
    Set<Genre> saveGenres(List<String> genreNames);
}
