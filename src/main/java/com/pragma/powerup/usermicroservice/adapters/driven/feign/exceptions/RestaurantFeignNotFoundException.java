package com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions;

public class RestaurantFeignNotFoundException extends RuntimeException{
    public RestaurantFeignNotFoundException(Throwable cause) {
        super(cause);
    }
}
