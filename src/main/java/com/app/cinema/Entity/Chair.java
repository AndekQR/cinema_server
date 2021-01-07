package com.app.cinema.Entity;

import com.app.cinema.helper.ChairType;
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
@Table(name="CHAIR")
public class Chair {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ChairType type;
    private Integer number;

    @ManyToOne()
    @JoinColumn(name="cinema_hall_id", nullable=false)
    private CinemaHall cinemaHall;


    @ManyToMany(mappedBy="chairs")
    private List<Reservation> reservations = new ArrayList<>();
}
