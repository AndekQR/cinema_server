package com.app.cinema.helper;

public class NotFoundInDB extends Exception {
    public NotFoundInDB(String msg) {
        super(msg);
    }
}
