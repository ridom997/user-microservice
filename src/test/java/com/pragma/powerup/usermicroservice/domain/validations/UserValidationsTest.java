package com.pragma.powerup.usermicroservice.domain.validations;

import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verifyNoMoreInteractions;

class UserValidationsTest {

    private User validUser = new User();

    @BeforeEach
    void setUp() {
        validUser.setMail("hol.aa.a@xa.com");
        validUser.setPhone("+123");
        validUser.setDniNumber("12345678");
        validUser.setPassword("password");
        validUser.setIdDniType("cc");
    }

    @Test
    void basicUserVariablesValidationsTest_invalidEmail() {
        this.validUser.setMail("mail");
        assertThrows(
                MailRegexException.class,
                () -> UserValidations.basicUserVariablesValidations(this.validUser));
    }

    @Test
    void basicUserVariablesValidationsTest_invalidPhone() {
        this.validUser.setPhone("asd");

        assertThrows(
                PhoneRegexException.class,
                () -> UserValidations.basicUserVariablesValidations(this.validUser));
    }

    @Test
    void basicUserVariablesValidationsTest_invalidPassword() {
        this.validUser.setPassword("");
        assertThrows(
                RuntimeException.class,
                () -> UserValidations.basicUserVariablesValidations(this.validUser));
    }

    @Test
    void basicUserVariablesValidationsTest_invalidDni() {
        this.validUser.setDniNumber("asd");
        assertThrows(
                DniRegexException.class,
                () -> UserValidations.basicUserVariablesValidations(this.validUser));
    }

    @Test
    void basicUserVariablesValidationsTest_success() {
        assertDoesNotThrow(() -> UserValidations.basicUserVariablesValidations(this.validUser));
    }

    @Test
    void verifyUserAgeTest_birthdayDateNotPresent() {
        assertThrows(
                RequiredVariableNotPresentException.class,
                () -> {
                    UserValidations.verifyUserAge(null);
                });
    }

    @Test
    void verifyUserAgeTest_birthdayDateNotValid() {
        String invalidBirthdayDate = "invalid_date";
        assertThrows(
                DateConvertException.class,
                () -> UserValidations.verifyUserAge(invalidBirthdayDate));
    }

    @Test
    void verifyUserAgeTest_userNotMoreThan18YearsOld() {
        String userBirthdayDate = "11-11-2020";
        assertThrows(
                UserAgeNotAllowedException.class,
                () -> UserValidations.verifyUserAge(userBirthdayDate));
    }

    @Test
    void verifyUserAgeTest_success() {
        String validBirthdayDate = "11-11-1990";
        assertDoesNotThrow(() -> UserValidations.verifyUserAge(validBirthdayDate));
    }

    @Test
    void verifyUserAgeTest_userIsNull() {
        assertThrows(
                RequiredVariableNotPresentException.class,
                () -> UserValidations.basicUserVariablesValidations(null));
    }
}