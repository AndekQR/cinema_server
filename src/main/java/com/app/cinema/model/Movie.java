package com.app.cinema.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="MOVIE")
public class Movie {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Basic
    private LocalDateTime startTime;
    private String title;
    private Integer year;
    private Integer runTimeMin;
    private String director;
    private String actors;
    @Column(length=512)
    private String plot;
    private String posteUrl;

    @ManyToMany(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinTable(
            name="MOVIE_GENRE",
            joinColumns=@JoinColumn(name="movie_id"),
            inverseJoinColumns=@JoinColumn(name="genre_id")
    )
    private Set<Genre> genres = new HashSet<>();


    @OneToMany(mappedBy="movie", fetch=FetchType.LAZY)
    private List<Reservation> reservations = new ArrayList<>();
}
