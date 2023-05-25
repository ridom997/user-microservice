package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public class LoginRequestDto {
    @NotBlank
    @Size(max = 255)
    @Email(regexp = Constants.MAIL_REGEX, message = "The mail provided has an incorrect format")
    private String mail;

    @NotBlank
    private String password;
}
