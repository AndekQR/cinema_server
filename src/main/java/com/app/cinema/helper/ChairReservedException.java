package com.app.cinema.helper;

public class ChairReservedException extends Exception {
    public ChairReservedException(Long chairId) {
        super("Chair id " + chairId + " already reserved");
    }
}
