package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort mockPersonPersistencePort;
    @Mock
    private IRolePersistencePort mockRolePersistencePort;

    private UserUseCase userUseCaseUnderTest;

    @BeforeEach
    void setUp() {
        userUseCaseUnderTest = new UserUseCase(mockPersonPersistencePort, mockRolePersistencePort);
    }

    @Test
    void testSaveUser() {
        // Setup
        final User user = new User(0L, "name", "surname", "mail", "phone", "address", "idDniType", "dniNumber",
                "idPersonType", "password", "birthday", new Role(0L, "name", "description"));

        // Run the test
        userUseCaseUnderTest.saveUser(user);

        // Verify the results
        verify(mockPersonPersistencePort).saveUser(any(User.class));
    }

    @Test
    void testSaveOwner_dateTimeParseException() {
        final User user = new User(null, "name", "surname", "mail@e.c", "+4123", "address", "idDniType", "123",
                "idPersonType", "password", "asdasd",null);
        assertThrows(DateConvertException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_userAgeNotAllowedException() {
        final User user = new User(null, "name", "surname", "mail@e.c", "+4123", "address", "idDniType", "123",
                "idPersonType", "password", "11-11-2020",null);
        assertThrows(UserAgeNotAllowedException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_mailRegexException() {
        final User user = new User(null, "name", "surname", "asd", "+4123", "address", "idDniType", "123",
                "idPersonType", "password", "11-11-1111",null);
        assertThrows(MailRegexException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_phoneRegexException() {
        final User user = new User(null, "name", "surname", "asd@c.c", "+sq4123", "address", "idDniType", "123",
                "idPersonType", "password", "11-11-1111",null);
        assertThrows(PhoneRegexException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_dniRegexException() {
        final User user = new User(null, "name", "surname", "asd@c.c", "+4123", "address", "idDniType", "asd",
                "idPersonType", "password", "11-11-1111",null);
        assertThrows(DniRegexException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_noRoleFounded() {
        // Setup
        final User user = new User(null, "name", "surname", "asd@c.c", "+4123", "address", "idDniType", "123",
                "idPersonType", "password", "11-11-1111",null);
        when(mockRolePersistencePort.getRoleById(3L)).thenThrow(new NoDataFoundException());
        assertThrows(NoDataFoundException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_requiredVariableNotPresentException() {
        // Setup
        final User user = new User(null, "name", "surname", null, "+4123", "address", "idDniType", "123",
                "idPersonType", "password", "11-11-1111",null);
        assertThrows(RequiredVariableNotPresentException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockPersonPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner() {
        // Setup
        final User user = new User(null, "name", "surname", "mail@e.c", "+4123", "address", "idDniType", "123",
                "idPersonType", "password", "11-11-1111",new Role(3L, "ROLE_OWNER", "ROLE_OWNER"));
        when(mockRolePersistencePort.getRoleById(3L)).thenReturn(new Role(3L, "ROLE_OWNER", "ROLE_OWNER"));

        // Run the test
        userUseCaseUnderTest.saveOwner(user);

        // Verify the results
        verify(mockPersonPersistencePort).saveUser(user);
        verify(mockRolePersistencePort).getRoleById(3L);
    }
}
