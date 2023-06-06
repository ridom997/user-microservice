package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.RequiredVariableNotPresentException;

import static com.pragma.powerup.usermicroservice.configuration.Constants.NOT_PRESENT_MESSAGE;

public class ArgumentValidations {

    private ArgumentValidations() {
    }

    public static void validateString(String str, String nameVariable){
        if(str == null)
            throw new RequiredVariableNotPresentException(nameVariable + NOT_PRESENT_MESSAGE);
    }

    public static void validateObject(Object obj, String nameObject){
        if(obj == null)
            throw new RequiredVariableNotPresentException(nameObject + NOT_PRESENT_MESSAGE);
    }
}
