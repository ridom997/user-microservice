package com.pragma.powerup.usermicroservice.domain.spi;

public interface IRestaurantValidationCommunicationPort {
    Boolean isTheRestaurantOwner(Long idRestaurant);
}
