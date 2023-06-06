package com.pragma.powerup.usermicroservice.domain.spi;

public interface ITokenValidationsPort {
    Long findIdUserFromToken(String token);
    void verifyRoleInToken(String token, String roleName);
}
