package com.app.cinema.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name="USERS")
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class User implements UserDetails, Serializable {

    @Id
    @Setter(AccessLevel.PRIVATE)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    @NotNull
    private String email;
    private Boolean verified;
    @NotNull
    private String password;

    @OneToMany(mappedBy="client", fetch=FetchType.LAZY, cascade={CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Reservation> reservations = new ArrayList<>();

    @ManyToMany(cascade={CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinTable(
            name="USER_AUTHORITY",
            joinColumns=@JoinColumn(name="user_id"),
            inverseJoinColumns=@JoinColumn(name="authority_id")
    )
    private Set<Authority> authorities=new HashSet<>();


    public User(String firstName, String lastName, String email, Boolean verified, String password, Set<Authority> authorities) {
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;
        this.verified=verified;
        this.password=password;
        this.authorities=authorities;
    }

    public void addReservation(Reservation reservation) {
        this.reservations.add(reservation);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities.stream().map(element ->
                new SimpleGrantedAuthority(element.getAuthorityType().name())).collect(Collectors.toList());
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    public List<String> getAuthoritiesList() {
        return authorities.stream().map(element ->
                element.getAuthorityType().name()
        ).collect(Collectors.toList());
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @JsonProperty(access=JsonProperty.Access.WRITE_ONLY)
    @Override
    public boolean isEnabled() {
        return true;
    }

}
