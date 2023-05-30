package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.model.User;

public interface IUserServicePort {
    void saveUser(User user);

    User saveOwner(User user);
    boolean userHasRole(Long idUser, Long idRole);

    User findUserById(Long idUser);

    User saveEmployee(User user, Long idRole, Long idRestaurant);
    User saveClient(User user, Long idRole);
}
