package com.app.cinema.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

}
