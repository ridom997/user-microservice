package com.pragma.powerup.usermicroservice.domain.constants;

public class DomainConstants {
    private DomainConstants() {}
    public static final Long CLIENT_ROLE_ID = 2L;
    public static final Long OWNER_ROLE_ID = 3L;
    public static final Long EMPLOYEE_ROLE_ID = 4L;
    public static final String ID_ROLE_MESSAGE = "Id role";
    public static final String PERSON_NOT_FOUND_MESSAGE = "No person found with the id: ";
    public static final String EMPLOYEE_ROLE_NAME = "ROLE_EMPLOYEE";
    public static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";
    public static final String MAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+(\\.[a-zA-Z0-9]+)$";
    public static final String PHONE_REGEX = "^\\+?\\d{1,12}$";
    public static final String DNI_REGEX = "^[0-9]{1,10}$";
    public static final String BIRTHDAY_DATE_FORMAT = "dd-MM-yyyy";
    public static final String NOT_PRESENT_MESSAGE = " is not present";
}
