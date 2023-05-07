package com.pragma.powerup.usermicroservice.domain.exceptions;

public class RequiredVariableNotPresentException extends RuntimeException{
    public RequiredVariableNotPresentException(Throwable cause) {
        super(cause);
    }
}
