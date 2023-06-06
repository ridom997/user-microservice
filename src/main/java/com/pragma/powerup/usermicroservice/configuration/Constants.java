package com.pragma.powerup.usermicroservice.configuration;

public class Constants {

    private Constants() {
        throw new IllegalStateException("Utility class");
    }


    public static final String RESPONSE_MESSAGE_KEY = "message";
    public static final String ROLES_KEY_MESSAGE = "roles";
    public static final String RESPONSE_BOOLEAN_RESULT_KEY = "result";
    public static final String PERSON_CREATED_MESSAGE = "Person created successfully";
    public static final String RESPONSE_ERROR_MESSAGE_KEY = "error";
    public static final String WRONG_CREDENTIALS_MESSAGE = "Wrong credentials or role not allowed";
    public static final String NO_DATA_FOUND_MESSAGE = "No data found for the requested petition";
    public static final String PERSON_ALREADY_EXISTS_MESSAGE = "A person already exists with the DNI number and DNI type provided";
    public static final String MAIL_ALREADY_EXISTS_MESSAGE = "A person with that mail already exists";
    public static final String PERSON_NOT_FOUND_MESSAGE = "No person found with the id provided";
    public static final String ROLE_NOT_FOUND_MESSAGE = "No role found with the id provided";
    public static final String ROLE_NOT_ALLOWED_MESSAGE = "No permission granted to create users with this role";
    public static final String USER_ALREADY_EXISTS_MESSAGE = "A user already exists with the role provided";
    public static final String USER_NOT_FOUND_MESSAGE = "No user found with the role provided";
    public static final String SWAGGER_TITLE_MESSAGE = "User API Pragma Power Up";
    public static final String SWAGGER_DESCRIPTION_MESSAGE = "User microservice";
    public static final String SWAGGER_VERSION_MESSAGE = "1.0.0";
    public static final String SWAGGER_LICENSE_NAME_MESSAGE = "Apache 2.0";
    public static final String SWAGGER_LICENSE_URL_MESSAGE = "http://springdoc.org";
    public static final String SWAGGER_TERMS_OF_SERVICE_MESSAGE = "http://swagger.io/terms/";
    public static final String MAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z0-9]+)$";
    public static final String PHONE_REGEX = "^\\+?\\d{1,12}$";
    public static final String DNI_REGEX = "^[0-9]{1,10}$";
    public static final String BIRTHDAY_DATE_FORMAT_REGEX = "^((0[1-9]|[12]\\d|3[01])-(0[1-9]|1[0-2])-\\d{4})$";
    public static final String BIRTHDAY_DATE_FORMAT = "dd-MM-yyyy";
    public static final Long OWNER_ROLE_ID = 3L;

    public static final String USER_AGE_NOT_ALLOWED = "User age is not valid";
    public static final String REQUIRED_VARIABLE_NOT_FOUND = "Some variable is missing";
    public static final String PHONE_NOT_VALID = "Phone is not valid";
    public static final String MAIL_NOT_VALID = "Mail is not valid";
    public static final String DNI_NOT_VALID = "Dni must have only numbers";
    public static final String ERROR_CONVERTING_DATE = "Error parsing birthday date";

    public static final String USER_ROLE_NOT_FOUND_MESSAGE = "No role associated with the user id provided has been found";
    public static final String AUTHORIZATION_HEADER = "Authorization";

    public static final Long EMPLOYEE_ROLE_ID = 4L;
    public static final Long CLIENT_ROLE_ID = 2L;
    public static final String INTERNAL_ERROR_APOLOGIZE_MESSAGE = "Something wrong happened, try again later!.";
    public static final String ID_ROLE_MESSAGE = "Id role";
    public static final String NOT_PRESENT_MESSAGE = " is not present";






}
