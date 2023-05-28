package com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions;

public class RestaurantFeignUnauthorizedException extends RuntimeException{
    public RestaurantFeignUnauthorizedException(Throwable cause) {
        super(cause);
    }
}
