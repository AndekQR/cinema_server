package com.app.cinema.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="CINEMA")
public class Cinema {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy="cinemaBuilding", cascade={CascadeType.ALL}, orphanRemoval=true)
    private List<CinemaHall> cinemaHalls = new ArrayList<>();

    public void addCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHalls.add(cinemaHall);
    }
}
