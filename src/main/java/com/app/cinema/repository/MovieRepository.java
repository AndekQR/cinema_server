package com.app.cinema.repository;

import com.app.cinema.Entity.Genre;
import com.app.cinema.Entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface MovieRepository extends JpaRepository<Movie, Long>, JpaSpecificationExecutor<Movie> {
    @Query("SELECT x FROM Movie x \n" +
            "WHERE x IN (\n" +
            "    SELECT y FROM Movie y\n" +
            "    INNER JOIN y.genres yt\n" +
            "    WHERE yt IN (\n" +
            "        :genres\n" +
            "    )\n" +
            "    GROUP BY y)")
//    List<Movie> findAllByGenres(Set<Genre> genres);
    Page<Movie> findAllByGenres(Set<Genre> genres, Pageable pageable);

    List<Movie> findAllByStartTimeBetween(LocalDateTime begin, LocalDateTime end);
}
