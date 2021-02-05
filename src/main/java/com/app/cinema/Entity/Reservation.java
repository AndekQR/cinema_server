package com.app.cinema.Entity;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Getter
    private Movie movie;

    @ManyToOne()
    @JoinColumn(name="cinema_hall_id")
    private CinemaHall cinemaHall;

    @CreationTimestamp
    private LocalDateTime createDateTime;

    @UpdateTimestamp
    private LocalDateTime updateDateTime;

    @NotNull
    private BigDecimal price;

    @ManyToMany(cascade={CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="RESERVATION_CHAIRS",
            joinColumns=@JoinColumn(name="reservation_id"),
            inverseJoinColumns=@JoinColumn(name="chair_id")
    )
    private List<Chair> chairs=new ArrayList<>();

    public void addChairToReservation(Chair chair) {
        this.chairs.add(chair);
    }


}
