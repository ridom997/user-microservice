package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.impl;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserAndRoleRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.adapters.driving.http.mapper.IUserRequestMapper;
import com.pragma.powerup.usermicroservice.configuration.security.jwt.JwtUtils;
import com.pragma.powerup.usermicroservice.domain.api.IUserServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserHandlerImpl implements IUserHandler {
    private final IUserServicePort personServicePort;
    private final IUserRequestMapper personRequestMapper;


    @Override
    public UserResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto) {
        return personRequestMapper.toUserResponse(personServicePort.saveEmployee(personRequestMapper.toUser(employeeRequestDto),
                employeeRequestDto.getIdRole(),employeeRequestDto.getIdRestaurant()));
    }

    @Override
    public UserResponseDto saveOwner(OwnerRequestDto ownerRequestDto) {
       return personRequestMapper.toUserResponse(personServicePort.saveOwner(personRequestMapper.toUser(ownerRequestDto)));
    }

    @Override
    public boolean userHasRole(UserAndRoleRequestDto userAndRoleRequestDto) {
        return personServicePort.userHasRole(userAndRoleRequestDto.getIdUser(),userAndRoleRequestDto.getIdRole());
    }

    @Override
    public UserResponseDto saveClient(ClientRequestDto clientRequestDto) {
        return personRequestMapper.toUserResponse(personServicePort.saveClient(personRequestMapper.toUser(clientRequestDto),
                clientRequestDto.getIdRole()));
    }

    @Override
    public boolean existsRelationWithUserAndIdRestaurant(Long idRestaurant) {
        return personServicePort.existsRelationWithUserAndIdRestaurant(idRestaurant, JwtUtils.getTokenFromRequestHeaders());
    }
}
