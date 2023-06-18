package com.pragma.powerup.usermicroservice.domain.constants;

import java.time.format.DateTimeFormatter;

import static com.pragma.powerup.usermicroservice.domain.constants.DomainConstants.BIRTHDAY_DATE_FORMAT;

public class SingletonConstants {

    private  SingletonConstants() {}
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(BIRTHDAY_DATE_FORMAT);


}
