package com.app.cinema.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    //    private Integer maxChairs; było na diagramie, nie wiem po co
    private Float screenSizeInch;

    @OneToMany(mappedBy="cinemaHall", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Chair> chairs = new ArrayList<>();

    @ManyToOne()
    @JoinColumn(name="cinema_id")
    private Cinema cinemaBuilding;

    @OneToMany(mappedBy="cinemaHall", fetch=FetchType.LAZY)
    private List<Reservation> reservation = new ArrayList<>();

    public void addChair(Chair chair) {
        this.chairs.add(chair);
    }


}
