package com.pragma.powerup.usermicroservice.domain.api;

import com.pragma.powerup.usermicroservice.domain.dto.UserBasicInfoDto;
import com.pragma.powerup.usermicroservice.domain.model.User;

import java.util.List;

public interface IUserServicePort {
    void saveUser(User user);

    User saveOwner(User user);
    boolean userHasRole(Long idUser, Long idRole);

    User findUserById(Long idUser);

    User saveEmployee(User user, Long idRole, Long idRestaurant);
    User saveClient(User user, Long idRole);

    Boolean existsRelationWithUserAndIdRestaurant(Long idRestaurant, String token);

    List<UserBasicInfoDto> getBasicInfoOfUsers(List<Long> userIdList, String token);
}
