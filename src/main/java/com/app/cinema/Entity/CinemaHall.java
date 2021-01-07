package com.app.cinema.Entity;

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
@Table(name="CINEMA_HALL")
public class CinemaHall {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;
    private Float screenSizeInch;

    @OneToMany(mappedBy="cinemaHall", cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Chair> chairs = new ArrayList<>();

    @ManyToOne(optional=false)
    @JoinColumn(name="cinema_id")
    private Cinema cinemaBuilding;

    @OneToMany(mappedBy="cinemaHall", fetch=FetchType.LAZY)
    private List<Reservation> reservation = new ArrayList<>();

    public void addChair(Chair chair) {
        this.chairs.add(chair);
    }


}
