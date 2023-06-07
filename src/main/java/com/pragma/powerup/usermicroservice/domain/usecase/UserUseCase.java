package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordActionsPort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantValidationCommunicationPort;
import com.pragma.powerup.usermicroservice.domain.spi.ITokenValidationsPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.ArgumentValidations;
import com.pragma.powerup.usermicroservice.domain.validations.UserValidations;

import static com.pragma.powerup.usermicroservice.configuration.Constants.EMPLOYEE_ROLE_NAME;
import static com.pragma.powerup.usermicroservice.configuration.Constants.ID_ROLE_MESSAGE;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleServicePort;

    private final IRestaurantValidationCommunicationPort restaurantValidationCommunicationPort;

    private final IPasswordActionsPort passwordActionsPort;
    private final ITokenValidationsPort tokenValidationsPort;

    public UserUseCase(IUserPersistencePort personPersistencePort, IRoleServicePort roleServicePort, IRestaurantValidationCommunicationPort restaurantValidationCommunicationPort, IPasswordActionsPort passwordActionsPort, ITokenValidationsPort tokenValidationsPort) {
        this.userPersistencePort = personPersistencePort;
        this.roleServicePort = roleServicePort;
        this.restaurantValidationCommunicationPort = restaurantValidationCommunicationPort;
        this.passwordActionsPort = passwordActionsPort;
        this.tokenValidationsPort = tokenValidationsPort;
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
        ArgumentValidations.validateObject(idRole,ID_ROLE_MESSAGE);
        ArgumentValidations.validateObject(idRestaurant,"Id restaurant");
        if (Boolean.FALSE.equals(restaurantValidationCommunicationPort.isTheRestaurantOwner(idRestaurant)))
            throw new ForbiddenActionException("The user who made the request does not have permission to create employees in this restaurant.");
        if(!idRole.equals(Constants.EMPLOYEE_ROLE_ID))
            throw new ForbiddenActionException("The user who made the request attempted to create a non-employee user.");
        UserValidations.verifyUserAge(user.getBirthday());
        UserValidations.basicUserVariablesValidations(user);
        user.setRole(roleServicePort.getRoleById(Constants.EMPLOYEE_ROLE_ID));
        user.setPassword(passwordActionsPort.encryptPassword(user.getPassword()));
        user.setIdRestaurant(idRestaurant);
        return userPersistencePort.saveUser(user);
    }

    @Override
    public User saveClient(User user, Long idRole) {
        ArgumentValidations.validateObject(idRole,ID_ROLE_MESSAGE);
        if(!idRole.equals(Constants.CLIENT_ROLE_ID))
            throw new ForbiddenActionException("The user who made the request attempted to create a non-client user.");
        UserValidations.basicUserVariablesValidations(user);
        user.setRole(roleServicePort.getRoleById(Constants.CLIENT_ROLE_ID));
        user.setPassword(passwordActionsPort.encryptPassword(user.getPassword()));
        return userPersistencePort.saveUser(user);
    }

    @Override
    public Boolean existsRelationWithUserAndIdRestaurant(Long idRestaurant, String token) {
        tokenValidationsPort.verifyRoleInToken(token,EMPLOYEE_ROLE_NAME);
        ArgumentValidations.validateObject(idRestaurant,"Id restaurant");
        Long idUserFromToken = tokenValidationsPort.findIdUserFromToken(token);
        User user = findUserById(idUserFromToken);
        if(user.getIdRestaurant() == null)
            throw new NoRestaurantAssociatedWithUserException();
        return user.getIdRestaurant().equals(idRestaurant);
    }


}
