package com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request;

import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class EmployeeRequestDto {
    @NotBlank
    @Size(max = 255)
    @Schema(example = "Monica")
    private String name;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "Galindo")
    private String surname;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = Constants.DNI_REGEX, message = "Dni number must be only numbers")
    @Schema(example = "1112332")
    private String dniNumber;

    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Phone is in bad format")
    @Schema(example = "+312311")
    private String phone;

    @NotBlank
    @Pattern(regexp = Constants.BIRTHDAY_DATE_FORMAT_REGEX, message = "Date must be in the format dd-MM-yyyy")
    @Schema(example = "11-11-1980")
    private String birthday;

    @NotBlank
    @Size(max = 255)
    @Email(regexp = Constants.MAIL_REGEX)
    @Schema(example = "no_repetir@g.c")
    private String mail;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "1234")
    private String password;

    @NotNull
    @Schema(example = "100")
    private Long idRestaurant;

    @NotNull
    @Schema(example = "4")
    private Long idRole;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "cc")
    private String idDniType;

    private String address;
    private String idPersonType;

}
