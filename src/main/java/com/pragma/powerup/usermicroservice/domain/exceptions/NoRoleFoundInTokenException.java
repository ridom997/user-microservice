package com.pragma.powerup.usermicroservice.domain.exceptions;

public class NoRoleFoundInTokenException extends RuntimeException{
    public NoRoleFoundInTokenException(String message) {
        super(message);
    }
}
