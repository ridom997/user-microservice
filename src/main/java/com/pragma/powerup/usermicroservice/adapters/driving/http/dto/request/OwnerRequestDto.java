package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class OwnerRequestDto {
    @NotBlank
    @Size(max = 255)
    private String name;

    @NotBlank
    @Size(max = 255)
    private String surname;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = Constants.DNI_REGEX, message = "Dni number must be only numbers")
    private String dniNumber;

    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Phone is in bad format")
    private String phone;

    @NotBlank
    @Pattern(regexp = Constants.BIRTHDAY_DATE_FORMAT_REGEX, message = "Date must be in the format dd-MM-yyyy")
    private String birthday;

    @NotBlank
    @Size(max = 255)
    @Email(regexp = Constants.MAIL_REGEX)
    private String mail;

    @NotBlank
    @Size(max = 255)
    private String password;

    private String address;
    private String idDniType;
    private String idPersonType;

}
