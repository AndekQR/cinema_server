package com.app.cinema.Entity;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name="GENRE")
public class Genre {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Getter
    private String name;

    @ManyToMany(mappedBy="genres", fetch=FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();

}
