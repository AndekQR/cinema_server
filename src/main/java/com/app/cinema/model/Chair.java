package com.app.cinema.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@Entity
@Table(name="CHAIR")
public class Chair {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String type;
    private Integer number;

    @ManyToOne
    @JoinColumn(name="cinema_hall_id")
    private CinemaHall cinemaHall;

    @ManyToOne
    @JoinColumn(name="reservation_id")
    private Reservation reservationTicket;
}
