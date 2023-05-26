package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort mockUserPersistencePort;
    @Mock
    private IRoleServicePort mockRoleServicePort;

    private UserUseCase userUseCaseUnderTest;

    @BeforeEach
    void setUp() {
        userUseCaseUnderTest = new UserUseCase(mockUserPersistencePort, mockRoleServicePort);
    }

    private User validUser(){
        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setSurname("surname");
        user.setMail("mail@e.c");
        user.setPhone("123321");
        user.setAddress("cra 123");
        user.setIdDniType("cc");
        user.setDniNumber("333");
        user.setIdPersonType("natural");
        user.setPassword("password");
        user.setBirthday("11-11-1990");
        user.setRole(new Role(0L, "name", "description"));
        return user;
    }

    @Test
    void testSaveUser() {
        // Run the test
        userUseCaseUnderTest.saveUser(validUser());

        // Verify the results
        verify(mockUserPersistencePort).saveUser(any(User.class));
    }

    @Test
    void testSaveOwner_dateTimeParseException() {
        final User user = validUser();
        user.setBirthday("asd");
        assertThrows(DateConvertException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_userAgeNotAllowedException() {
        final User user = validUser();
        user.setBirthday("11-11-2020");
        assertThrows(UserAgeNotAllowedException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_mailRegexException() {
        final User user = validUser();
        user.setMail("2020");
        assertThrows(MailRegexException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_phoneRegexException() {
        final User user = validUser();
        user.setPhone("ewqe20");
        assertThrows(PhoneRegexException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_dniRegexException() {
        final User user = validUser();
        user.setDniNumber("1sd2020");
        assertThrows(DniRegexException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_noRoleFounded() {
        final User user = validUser();
        user.setRole(null);
        when(mockRoleServicePort.getRoleById(3L)).thenThrow(new NoDataFoundException());
        assertThrows(NoDataFoundException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_requiredVariableNotPresentException() {
        final User user = validUser();
        user.setPhone(null);
        assertThrows(RequiredVariableNotPresentException.class, () -> userUseCaseUnderTest.saveOwner(user));
        verify(mockUserPersistencePort, times(0)).saveUser(user);
    }

    @Test
    void testSaveOwner_successfully() {
        final User user = validUser();
        user.setRole(new Role(3L, "ROLE_OWNER", "ROLE_OWNER"));
        when(mockRoleServicePort.getRoleById(3L)).thenReturn(new Role(3L, "ROLE_OWNER", "ROLE_OWNER"));
        when(mockUserPersistencePort.saveUser(user)).thenReturn(user);
        // Run the test
        User result = userUseCaseUnderTest.saveOwner(user);

        // Verify the results
        assertEquals(user,result);
        verify(mockUserPersistencePort).saveUser(user);
        verify(mockRoleServicePort).getRoleById(3L);
    }

    @Test
    void testUserHasRole_true() {
        final User user = validUser();
        user.setId(1L);
        user.setRole(new Role(3L, "ROLE_OWNER", "ROLE_OWNER"));
        Long idUser = 1l;
        Long idRole = 3l;
        when(mockUserPersistencePort.findUserById(1L)).thenReturn(user);

        Boolean response = userUseCaseUnderTest.userHasRole(idUser, idRole);

        verify(mockUserPersistencePort).findUserById(1L);
        assertTrue(response);
    }

    @Test
    void testUserHasRole_false() {
        final User user = validUser();
        user.setId(1L);
        user.setRole(new Role(2L, "ROLE_OWNER", "ROLE_OWNER"));
        Long idUser = 1l;
        Long idRole = 3l;
        when(mockUserPersistencePort.findUserById(1L)).thenReturn(user);

        Boolean response = userUseCaseUnderTest.userHasRole(idUser, idRole);

        verify(mockUserPersistencePort).findUserById(1L);
        assertFalse(response);
    }

    @Test
    void testUserHasRole_requiredVariableNotPresentException() {
        final User user = validUser();
        user.setId(1L);
        user.setRole(new Role(2L, "ROLE_OWNER", "ROLE_OWNER"));
        Long idUser = 1l;
        Long idRole = null;

        assertThrows(RequiredVariableNotPresentException.class, () -> userUseCaseUnderTest.userHasRole(idUser, idRole));
        verify(mockUserPersistencePort,times(0)).findUserById(1L);
    }

    @Test
    void testUserHasRole_userDoesntHaveRoleException() {
        final User user = validUser();
        user.setId(1L);
        user.setRole(null);
        Long idUser = 1l;
        Long idRole = 3L;
        when(mockUserPersistencePort.findUserById(1L)).thenReturn(user);

        assertThrows(UserDoesntHaveRoleException.class, () -> userUseCaseUnderTest.userHasRole(idUser, idRole));

        verify(mockUserPersistencePort).findUserById(1L);
    }

    @Test
    void testFindUserById_successfully() {
        final User user = validUser();
        user.setId(1L);

        Long idUser = 1l;
        when(mockUserPersistencePort.findUserById(1L)).thenReturn(user);

        User response = userUseCaseUnderTest.findUserById(idUser);

        verify(mockUserPersistencePort).findUserById(1L);
        assertEquals(user.getId(),response.getId());
    }

    @Test
    void testFindUserById_userDoesntExistException() {
        Long idUser = 1l;
        when(mockUserPersistencePort.findUserById(1L)).thenReturn(null);

        assertThrows(UserDoesntExistException.class, () -> userUseCaseUnderTest.findUserById(idUser));

        verify(mockUserPersistencePort).findUserById(1L);
    }
}
