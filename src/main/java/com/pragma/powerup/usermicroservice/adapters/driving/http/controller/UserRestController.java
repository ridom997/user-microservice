package com.pragma.powerup.usermicroservice.adapters.driving.http.controller;

import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.EmployeeRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.OwnerRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.request.UserAndRoleRequestDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.dto.response.UserResponseDto;
import com.pragma.powerup.usermicroservice.adapters.driving.http.handlers.IUserHandler;
import com.pragma.powerup.usermicroservice.configuration.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserRestController {
    private final IUserHandler personHandler;

    @Operation(summary = "Add a new owner",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Owner created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Owner role in db not found",
                            content = @Content(mediaType = "application/json",  schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "409", description = "User already exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "400", description = "Bad request, check response message",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
    })
    @PostMapping(path = "createOwner")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<UserResponseDto> saveOwner(@RequestBody @Valid OwnerRequestDto ownerRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personHandler.saveOwner(ownerRequestDto));
    }

    @Operation(summary = "Validate if user has a role",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Role was checked",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Map"))),
                    @ApiResponse(responseCode = "404", description = "User doesn't exists",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "400", description = "Bad request, check response message",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
    })
    @PostMapping(path = "validateRole")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<Map<String, Boolean>> userHasRole(@RequestBody @Valid UserAndRoleRequestDto userAndRoleRequestDto) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(Collections.singletonMap(Constants.RESPONSE_BOOLEAN_RESULT_KEY, personHandler.userHasRole(userAndRoleRequestDto)));
    }

    @Operation(summary = "Add a new employee",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Employee created",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponseDto.class))),
                    @ApiResponse(responseCode = "409", description = "User already exist",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "400", description = "Bad request, check response message",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "401", description = "Unauthorized request",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "403", description = "Attempted unauthorized action",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "404", description = "Restaurant or role in db not found",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error"))),
                    @ApiResponse(responseCode = "500", description = "Fail in communication with foodcourt-microservice",
                            content = @Content(mediaType = "application/json", schema = @Schema(ref = "#/components/schemas/Error")))
            })
    @PostMapping(path = "createEmployee")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<UserResponseDto> saveEmployee(@RequestBody @Valid EmployeeRequestDto employeeRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(personHandler.saveEmployee(employeeRequestDto));
    }
}
