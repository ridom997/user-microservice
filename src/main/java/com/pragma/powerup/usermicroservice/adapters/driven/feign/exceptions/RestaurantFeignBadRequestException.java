package com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions;

public class RestaurantFeignBadRequestException extends RuntimeException{
    public RestaurantFeignBadRequestException(Throwable cause) {
        super(cause);
    }
}
