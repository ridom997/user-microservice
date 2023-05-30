package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.configuration.SingletonConstants;
import com.pragma.powerup.usermicroservice.configuration.utils.AgeUtils;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.User;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

public class UserValidations {

    private UserValidations(){}

    public static void basicUserVariablesValidations(User user) {
        if(user == null)
            throw new RequiredVariableNotPresentException("User is not present");
        validateStringRegex("Mail", user.getMail(), MAIL_REGEX, new MailRegexException());
        validateStringRegex("Phone", user.getPhone(), PHONE_REGEX, new PhoneRegexException());
        validateStringRegex("Dni number", user.getDniNumber(), DNI_REGEX, new DniRegexException());
        validateThatStringVariableIsPresent("Dni type", user.getIdDniType());
        validateThatStringVariableIsPresent("Password", user.getPassword());
    }

    public static void verifyUserAge(String userBirthdayDate) {
        if (userBirthdayDate == null || userBirthdayDate.isEmpty())
            throw new RequiredVariableNotPresentException("Birthday date is not present");

        try {
            LocalDate birthdayDate = LocalDate.parse(userBirthdayDate, SingletonConstants.dateTimeFormatter);
            if (!AgeUtils.isMoreThan18YearsOld(birthdayDate))
                throw new UserAgeNotAllowedException();
        } catch (DateTimeParseException e) {
            throw new DateConvertException(e);
        }
    }

    private static void validateStringRegex(String nameVariable, String text, String regex, RuntimeException throwableClass) {
        validateThatStringVariableIsPresent(nameVariable, text);
        if (!text.matches(regex))
            throw throwableClass;
    }

    private static void validateThatStringVariableIsPresent(String nameVariable, String text) {
        if (text == null || text.isEmpty())
            throw new RequiredVariableNotPresentException(nameVariable + " is not present");
    }

}
