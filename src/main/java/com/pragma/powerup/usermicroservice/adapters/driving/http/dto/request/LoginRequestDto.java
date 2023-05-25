package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "email@some.com")
    private String mail;

    @NotBlank
    @Schema(example = "1234")
    private String password;
}
