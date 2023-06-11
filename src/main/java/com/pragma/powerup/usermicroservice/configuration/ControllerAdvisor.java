package com.pragma.powerup.usermicroservice.configuration;

import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.FailConnectionToExternalMicroserviceException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.RestaurantFeignBadRequestException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.RestaurantFeignNotFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.feign.exceptions.RestaurantFeignUnauthorizedException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.MailAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.PersonAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.RoleNotAllowedForCreationException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserAlreadyExistsException;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.UserNotFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.pragma.powerup.usermicroservice.configuration.Constants.*;

@ControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> errorMessages = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            if (error instanceof FieldError fieldError) {
                errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
            } else {
                errorMessages.add(error.getDefaultMessage());
            }
        }
        return new ResponseEntity<>(errorMessages, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, WRONG_CREDENTIALS_MESSAGE));
    }

    @ExceptionHandler(NoDataFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoDataFoundException(NoDataFoundException noDataFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, NO_DATA_FOUND_MESSAGE));
    }
    @ExceptionHandler(PersonAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handlePersonAlreadyExistsException(
            PersonAlreadyExistsException personAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PERSON_ALREADY_EXISTS_MESSAGE));
    }

    @ExceptionHandler(MailAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleMailAlreadyExistsException(
            MailAlreadyExistsException mailAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, MAIL_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(RoleNotAllowedForCreationException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotAllowedForCreationException(
            RoleNotAllowedForCreationException roleNotAllowedForCreationException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_ALLOWED_MESSAGE));
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(
            UserAlreadyExistsException userAlreadyExistsException) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_ALREADY_EXISTS_MESSAGE));
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserNotFoundException userNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRoleNotFoundException(
            RoleNotFoundException roleNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ROLE_NOT_FOUND_MESSAGE));
    }
    @ExceptionHandler(UserAgeNotAllowedException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserAgeNotAllowedException userAgeNotAllowedException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_AGE_NOT_ALLOWED));
    }

    @ExceptionHandler(RequiredVariableNotPresentException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            RequiredVariableNotPresentException requiredVariableNotPresentException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, requiredVariableNotPresentException.getMessage()));
    }

    @ExceptionHandler(PhoneRegexException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            PhoneRegexException phoneRegexException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, PHONE_NOT_VALID));
    }

    @ExceptionHandler(MailRegexException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            MailRegexException mailRegexException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, MAIL_NOT_VALID));
    }

    @ExceptionHandler(DniRegexException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            DniRegexException dniRegexException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, DNI_NOT_VALID));
    }

    @ExceptionHandler(DateConvertException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            DateConvertException dateConvertException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, ERROR_CONVERTING_DATE));
    }

    @ExceptionHandler(UserDoesntHaveRoleException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserDoesntHaveRoleException userDoesntHaveRoleException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, USER_ROLE_NOT_FOUND_MESSAGE));
    }


    @ExceptionHandler(UserDoesntExistException.class)
    public ResponseEntity<Map<String, String>> handleUserNotFoundException(
            UserDoesntExistException userDoesntExistException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, userDoesntExistException.getMessage()));
    }

    @ExceptionHandler(FailConnectionToExternalMicroserviceException.class)
    public ResponseEntity<Map<String, String>> handleFailConnectionToExternalMicroserviceException(FailConnectionToExternalMicroserviceException failConnectionToExternalMicroserviceException) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, INTERNAL_ERROR_APOLOGIZE_MESSAGE));
    }

    @ExceptionHandler(ForbiddenActionException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedExceptionException(
            ForbiddenActionException forbiddenActionException) {
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, forbiddenActionException.getMessage()));
    }

    @ExceptionHandler(RestaurantFeignBadRequestException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantFeignBadRequestException(
            RestaurantFeignBadRequestException restaurantFeignBadRequestException) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Required variable is missing or Restaurant doesn't have owner"));
    }
    @ExceptionHandler(RestaurantFeignNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantFeignNotFoundException(
            RestaurantFeignNotFoundException restaurantFeignNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Restaurant not found"));
    }

    @ExceptionHandler(RestaurantFeignUnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleRestaurantFeignUnauthorizedException(
            RestaurantFeignUnauthorizedException restaurantFeignUnauthorizedException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Unauthorized request to microservice or idUser doesn't exists in token"));
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ResponseEntity<Map<String, String>> handleParsingExceptions(Exception exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Error parsing a request variable"));
    }

    @ExceptionHandler(NoIdUserFoundInTokenException.class)
    public ResponseEntity<Map<String, String>> handleNoIdUserFoundInTokenException(NoIdUserFoundInTokenException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "Id user not found in jwt token"));
    }

    @ExceptionHandler(NoRoleFoundInTokenException.class)
    public ResponseEntity<Map<String, String>> handleNoRoleFoundInTokenException(NoRoleFoundInTokenException noRoleFoundInTokenException) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, noRoleFoundInTokenException.getMessage()));
    }

    @ExceptionHandler(NoRestaurantAssociatedWithUserException.class)
    public ResponseEntity<Map<String, String>> handleNoRestaurantAssociatedWithUserException(NoRestaurantAssociatedWithUserException noRestaurantAssociatedWithUserException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Collections.singletonMap(RESPONSE_ERROR_MESSAGE_KEY, "User doesn't have a idRestaurant associated"));
    }
}
