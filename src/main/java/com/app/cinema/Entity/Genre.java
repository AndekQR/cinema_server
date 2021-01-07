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
@Table(name="GENRE")
public class Genre {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToMany(mappedBy="genres", fetch=FetchType.LAZY)
    private List<Movie> movies = new ArrayList<>();

}
