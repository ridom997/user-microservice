package com.pragma.powerup.usermicroservice.configuration.utils;

import java.time.LocalDate;
import java.time.Period;

public class AgeUtils {
    private AgeUtils() {
    }

    public static boolean isMoreThan18YearsOld(LocalDate birthdayDate) {
        LocalDate actualDate = LocalDate.now();
        Period difference = Period.between(birthdayDate, actualDate);
        int age = difference.getYears();
        return age >= 18;
    }
}
