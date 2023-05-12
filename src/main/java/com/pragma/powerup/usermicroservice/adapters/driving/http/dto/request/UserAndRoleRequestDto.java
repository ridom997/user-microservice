package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserAndRoleRequestDto {
    @NotNull
    private Long idUser;
    @NotNull
    private Long idRole;
}