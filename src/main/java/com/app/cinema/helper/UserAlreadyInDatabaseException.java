package com.app.cinema.helper;

public class UserAlreadyInDatabaseException extends Exception {
    public UserAlreadyInDatabaseException(String msg) {super(msg);}
}