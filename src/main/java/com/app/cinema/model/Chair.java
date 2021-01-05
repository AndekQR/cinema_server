package com.app.cinema.model;

import com.app.cinema.helper.ChairType;
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

    @Enumerated(EnumType.STRING)
    private ChairType type;
    private Integer number;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="cinema_hall_id", nullable=false)
    private CinemaHall cinemaHall;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="reservation_id")
    private Reservation reservationTicket;
}
