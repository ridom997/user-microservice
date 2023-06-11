package com.pragma.powerup.usermicroservice.domain.mappers;

import com.pragma.powerup.usermicroservice.domain.dto.UserBasicInfoDto;
import com.pragma.powerup.usermicroservice.domain.model.User;

public class UserMapper {

    private UserMapper() { }

    public static UserBasicInfoDto mapToUserBasicInfoDto(User user){
        UserBasicInfoDto userBasicInfoDto = new UserBasicInfoDto();
        userBasicInfoDto.setId(user.getId());
        userBasicInfoDto.setMail(user.getMail());
        userBasicInfoDto.setName(user.getName() + " " + user.getSurname());
        userBasicInfoDto.setPhone(user.getPhone());
        return userBasicInfoDto;
    }
}
