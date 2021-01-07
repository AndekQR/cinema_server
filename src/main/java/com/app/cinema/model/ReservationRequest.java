package com.app.cinema.model;

import lombok.Data;

import java.util.List;

@Data
public class ReservationRequest {
    List<Long> chairIds;
    Long moveId;
    Float price;
}
