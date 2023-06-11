package com.pragma.powerup.usermicroservice.domain.exceptions;

public class UserDoesntExistException extends RuntimeException{
    public UserDoesntExistException(String message) {
        super(message);
    }
}