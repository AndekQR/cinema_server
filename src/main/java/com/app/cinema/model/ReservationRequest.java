package com.app.cinema.model;

import lombok.Data;

import java.util.List;

@Data
public class ReservationRequest {
    List<Long> chairIds;
    // TODO to powinno być movieId przecież, nie zmieniam żeby nie psuć Api
    Long moveId;
    Float price;
}
