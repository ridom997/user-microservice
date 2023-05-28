package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.RequiredVariableNotPresentException;
import com.pragma.powerup.usermicroservice.domain.exceptions.ForbiddenActionException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserDoesntExistException;
import com.pragma.powerup.usermicroservice.domain.exceptions.UserDoesntHaveRoleException;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordActionsPort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantValidationCommunicationPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.UserValidations;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleServicePort;

    private final IRestaurantValidationCommunicationPort restaurantValidationCommunicationPort;

    private final IPasswordActionsPort passwordActionsPort;

    public UserUseCase(IUserPersistencePort personPersistencePort, IRoleServicePort roleServicePort, IRestaurantValidationCommunicationPort restaurantValidationCommunicationPort, IPasswordActionsPort passwordActionsPort) {
        this.userPersistencePort = personPersistencePort;
        this.roleServicePort = roleServicePort;
        this.restaurantValidationCommunicationPort = restaurantValidationCommunicationPort;
        this.passwordActionsPort = passwordActionsPort;
    }

    @Override
    public void saveUser(User user) {
        userPersistencePort.saveUser(user);
    }

    @Override
    public User saveOwner(User user) {
        UserValidations.verifyUserAge(user.getBirthday());
        UserValidations.basicUserVariablesValidations(user);
        user.setRole(roleServicePort.getRoleById(Constants.OWNER_ROLE_ID));
        user.setPassword(passwordActionsPort.encryptPassword(user.getPassword()));
        return userPersistencePort.saveUser(user);
    }

    @Override
    public boolean userHasRole(Long idUser, Long idRole) {
        if (idUser == null || idRole == null)
            throw new RequiredVariableNotPresentException();
        User user = findUserById(idUser);
        if(user.getRole() == null)
            throw new UserDoesntHaveRoleException();
        return user.getRole().getId().equals(idRole);
    }

    @Override
    public User findUserById(Long idUser) {
        User user = userPersistencePort.findUserById(idUser);
        if (user == null)
            throw new UserDoesntExistException();
        return user;
    }

    @Override
    public User saveEmployee(User user, Long idRole, Long idRestaurant) {
        if (Boolean.FALSE.equals(restaurantValidationCommunicationPort.isTheRestaurantOwner(idRestaurant)))
            throw new ForbiddenActionException("The user who made the request does not have permission to create employees in this restaurant.");
        if(!idRole.equals(Constants.EMPLOYEE_ROLE_ID))
            throw new ForbiddenActionException("The user who made the request attempted to create a non-employee user.");
        UserValidations.verifyUserAge(user.getBirthday());
        UserValidations.basicUserVariablesValidations(user);
        user.setRole(roleServicePort.getRoleById(Constants.EMPLOYEE_ROLE_ID));
        user.setPassword(passwordActionsPort.encryptPassword(user.getPassword()));
        return userPersistencePort.saveUser(user);
    }

}
