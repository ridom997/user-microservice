package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.MailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IUserEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IUserRepository;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserMysqlAdapter implements IUserPersistencePort {
    private final IUserRepository personRepository;
    private final IUserEntityMapper personEntityMapper;

    @Override
    public User saveUser(User user) {
        if(user.getIdDniType() == null && personRepository.findByDniNumber(user.getDniNumber()).isPresent()){
                throw new PersonAlreadyExistsException();
        }

        if (personRepository.findByDniNumberAndIdDniType(user.getDniNumber(), user.getIdDniType()).isPresent()) {
            throw new PersonAlreadyExistsException();
        }

        if (personRepository.existsByMail(user.getMail())){
            throw new MailAlreadyExistsException();
        }

        return personEntityMapper.toUser(personRepository.save(personEntityMapper.toEntity(user)));
    }

    @Override
    public User findUserById(Long idUser) {
        Optional<UserEntity> userEntityOptional = personRepository.findById(idUser);
        if(!userEntityOptional.isPresent())
            return null;
        return personEntityMapper.toUser(userEntityOptional.get());
    }
}
