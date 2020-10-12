package com.app.cinema.model;

import com.app.cinema.helper.AuthorityType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="AUTHORITIES")
@ToString
public class Authority implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Setter
    @Getter
    @Enumerated(EnumType.STRING)
    private AuthorityType authorityType;

    public Authority(){}
    public Authority(AuthorityType authorityType) {
        this.authorityType=authorityType;
    }

}
