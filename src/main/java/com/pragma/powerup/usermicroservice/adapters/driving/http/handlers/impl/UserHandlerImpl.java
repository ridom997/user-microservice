package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserAndRoleRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserServicePort personServicePort;
    private final IUserRequestMapper personRequestMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {
        personServicePort.saveUser(personRequestMapper.toUser(userRequestDto));
    }

    @Override
    public void saveOwner(OwnerRequestDto ownerRequestDto) {
        personServicePort.saveOwner(personRequestMapper.toUser(ownerRequestDto));
    }

    @Override
    public boolean userHasRole(UserAndRoleRequestDto userAndRoleRequestDto) {
        return personServicePort.userHasRole(userAndRoleRequestDto.getIdUser(),userAndRoleRequestDto.getIdRole());
    }
}
