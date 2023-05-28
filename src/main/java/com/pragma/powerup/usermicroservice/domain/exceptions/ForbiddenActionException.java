package com.pragma.powerup.usermicroservice.domain.exceptions;

public class ForbiddenActionException extends RuntimeException{
    public ForbiddenActionException(String message) {
        super(message);
    }
}
