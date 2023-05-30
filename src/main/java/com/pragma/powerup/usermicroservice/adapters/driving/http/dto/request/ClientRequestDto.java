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
public class ClientRequestDto {
    @NotBlank
    @Size(max = 255)
    @Schema(example = "Berna")
    private String name;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "Salazar")
    private String surname;

    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = Constants.DNI_REGEX, message = "Dni number must be only numbers")
    @Schema(example = "1121000")
    private String dniNumber;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "cc")
    private String idDniType;

    @NotBlank
    @Size(max = 13)
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Phone is in bad format")
    @Schema(example = "+312311")
    private String phone;

    @NotBlank
    @Size(max = 255)
    @Email(regexp = Constants.MAIL_REGEX)
    @Schema(example = "el.cliente@pragma.com")
    private String mail;

    @NotBlank
    @Size(max = 255)
    @Schema(example = "1234")
    private String password;

    @NotNull
    @Schema(example = "2")
    private Long idRole;

    private String address;
    private String idPersonType;
}
