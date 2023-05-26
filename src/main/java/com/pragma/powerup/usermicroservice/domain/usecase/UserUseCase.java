package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import com.pragma.powerup.usermicroservice.configuration.SingletonConstants;
import com.pragma.powerup.usermicroservice.configuration.utils.AgeUtils;
import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UserUseCase implements IUserServicePort {
    private final IUserPersistencePort userPersistencePort;
    private final IRoleServicePort roleServicePort;


    public UserUseCase(IUserPersistencePort personPersistencePort, IRoleServicePort roleServicePort) {
        this.userPersistencePort = personPersistencePort;
        this.roleServicePort = roleServicePort;
    }

    @Override
    public void saveUser(User user) {
        userPersistencePort.saveUser(user);
    }

    @Override
    public User saveOwner(User user) {
        try{
            LocalDate birthdayDate = LocalDate.parse(user.getBirthday(), SingletonConstants.dateTimeFormatter);
            if (!AgeUtils.isMoreThan18YearsOld(birthdayDate))
                throw new UserAgeNotAllowedException();
            if (!user.getMail().matches(Constants.MAIL_REGEX))
                throw new MailRegexException();
            if (!user.getPhone().matches(Constants.PHONE_REGEX))
                throw new PhoneRegexException();
            if (!user.getDniNumber().matches(Constants.DNI_REGEX))
                throw new DniRegexException();
            user.setRole(roleServicePort.getRoleById(Constants.OWNER_ROLE_ID));
            return userPersistencePort.saveUser(user);
        } catch (DateTimeParseException e){
            throw new DateConvertException(e);
        } catch (NullPointerException e){
            throw new RequiredVariableNotPresentException(e);
        }
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

}
