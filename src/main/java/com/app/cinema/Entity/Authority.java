package com.app.cinema.Entity;

import com.app.cinema.helper.AuthorityType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="AUTHORITIES")
@NoArgsConstructor
@ToString
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    @ManyToMany(mappedBy="authorities")
    private List<User> users = new ArrayList<>();

    public Authority(AuthorityType authorityType) {
        this.authorityType=authorityType;
    }



}
