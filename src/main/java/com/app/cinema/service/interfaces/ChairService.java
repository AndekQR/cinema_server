package com.app.cinema.service.interfaces;

import com.app.cinema.helper.NotFoundInDB;
import com.app.cinema.Entity.Chair;

public interface ChairService {

    Chair findChair(Long chairId) throws NotFoundInDB;
    Boolean isReserved(Long chairId, Long movieId) throws NotFoundInDB;
}
