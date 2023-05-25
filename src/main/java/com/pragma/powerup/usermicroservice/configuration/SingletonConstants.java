package com.pragma.powerup.usermicroservice.configuration;

import java.time.format.DateTimeFormatter;

public class SingletonConstants {

    private  SingletonConstants() {}
    public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.BIRTHDAY_DATE_FORMAT);


}
