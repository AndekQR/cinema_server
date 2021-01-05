package com.app.cinema.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@Entity
@Table(name="RESERVATION")
public class Reservation {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User client;

    @ManyToOne
    @JoinColumn(name="movie_id")
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name="cinema_hall_id")
    private CinemaHall cinemaHall;

    @NotNull
    private BigInteger price;

    @OneToMany(mappedBy="reservationTicket", fetch=FetchType.LAZY)
    private List<Chair> chairs = new ArrayList<>();


}
