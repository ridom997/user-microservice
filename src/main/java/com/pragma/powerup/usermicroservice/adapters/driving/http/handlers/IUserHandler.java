package com.pragma.powerup.usermicroservice.adapters.driving.http.handlers;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.ClientRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserAndRoleRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.domain.dto.UserBasicInfoDto;

import java.util.List;

public interface IUserHandler {
    UserResponseDto saveEmployee(EmployeeRequestDto employeeRequestDto);
    UserResponseDto saveOwner(OwnerRequestDto ownerRequestDto);
    boolean userHasRole(UserAndRoleRequestDto userAndRoleRequestDto);

    UserResponseDto saveClient(ClientRequestDto clientRequestDto);

    boolean existsRelationWithUserAndIdRestaurant(Long idRestaurant);

    List<UserBasicInfoDto> getBasicInfoOfUsers(List<Long> userIdList);
}
