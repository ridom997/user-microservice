package com.pragma.powerup.usermicroservice.domain.spi;

public interface IPasswordActionsPort {
    String encryptPassword(String password);
}
