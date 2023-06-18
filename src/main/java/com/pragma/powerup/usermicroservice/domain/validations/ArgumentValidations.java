package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.RequiredVariableNotPresentException;

import java.util.List;

import static com.pragma.powerup.usermicroservice.domain.constants.DomainConstants.NOT_PRESENT_MESSAGE;

public class ArgumentValidations {

    private ArgumentValidations() {
    }

    public static void validateString(String str, String nameVariable){
        if(str == null || str.isEmpty())
            throw new RequiredVariableNotPresentException(nameVariable + NOT_PRESENT_MESSAGE);
    }

    public static void validateObject(Object obj, String nameObject){
        if(obj == null)
            throw new RequiredVariableNotPresentException(nameObject + NOT_PRESENT_MESSAGE);
    }

    public static void validateList(List<Long> obj, String nameList){
        if(obj == null || obj.isEmpty())
            throw new RequiredVariableNotPresentException(nameList + NOT_PRESENT_MESSAGE);
    }
}
