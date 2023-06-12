package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.exceptions.NoDataFoundException;
import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.dto.UserBasicInfoDto;
import com.pragma.powerup.usermicroservice.domain.exceptions.*;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IPasswordActionsPort;
import com.pragma.powerup.usermicroservice.domain.spi.IRestaurantValidationCommunicationPort;
import com.pragma.powerup.usermicroservice.domain.spi.ITokenValidationsPort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import com.pragma.powerup.usermicroservice.domain.validations.ArgumentValidations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.pragma.powerup.usermicroservice.configuration.Constants.ADMIN_ROLE_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @Mock
    private IUserPersistencePort mockUserPersistencePort;
    @Mock
    private IRoleServicePort mockRoleServicePort;

    @Mock
    private IRestaurantValidationCommunicationPort restaurantValidationCommunicationPort;

    @Mock
    private IPasswordActionsPort passwordActionsPort;

    @Mock
    private ITokenValidationsPort tokenValidationsPort;
    private Role employeeRole = new Role(4L, "ROLE_EMPLOYEE", "ROLE_EMPLOYEE");
    private Role clientRole = new Role(2L,"ROLE_CLIENT","ROLE_CLIENT");
    private UserUseCase userUseCaseUnderTest;

    @BeforeEach
    void setUp() {
        userUseCaseUnderTest = new UserUseCase(mockUserPersistencePort, mockRoleServicePort, restaurantValidationCommunicationPort, passwordActionsPort, tokenValidationsPort);
    }

    private User validUser(){
        User user = new User();
        user.setId(0L);
        user.setName("name");
        user.setSurname("surname");
        user.setMail("mai.l@we.e");
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

    private User validClient(){
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        user.setDniNumber("333");
        user.setIdDniType("cc");
        user.setPhone("123321");
        user.setMail("mai.l@we.e");
        user.setPassword("password");
        user.setRole(new Role(2L, "ROLE_USER", "ROLE_USER"));
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
    void testSaveOwner_birthdayVariableNotPresentException() {
        final User user = validUser();
        user.setBirthday(null);
        RequiredVariableNotPresentException requiredVariableNotPresentException = assertThrows(RequiredVariableNotPresentException.class, () -> userUseCaseUnderTest.saveOwner(user));
        assertEquals("Birthday date is not present",requiredVariableNotPresentException.getMessage());
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

    @Test
    void saveEmployeeTest_successfully() {
        String encryptedPassword = "encrypted";
        Long idRole = 4L;
        User expectedUser = validUser();
        expectedUser.setRole(this.employeeRole);
        expectedUser.setPassword(encryptedPassword);
        Long idRestaurant = 100L;
        when(restaurantValidationCommunicationPort.isTheRestaurantOwner(idRestaurant)).thenReturn(true);
        when(mockRoleServicePort.getRoleById(4L)).thenReturn(employeeRole);
        when(passwordActionsPort.encryptPassword(anyString())).thenReturn("encryptedbcrypt");
        when(mockUserPersistencePort.saveUser(any(User.class))).thenReturn(expectedUser);

        User result = userUseCaseUnderTest.saveEmployee(expectedUser, idRole, idRestaurant);

        assertEquals(expectedUser,result);
        verify(restaurantValidationCommunicationPort, times(1)).isTheRestaurantOwner(idRestaurant);
        verify(mockRoleServicePort, times(1)).getRoleById(4L);
        verify(passwordActionsPort, times(1)).encryptPassword(anyString());

    }

    @Test
    void saveEmployeeTest_forbiddenActionExceptionUserIsNotOwner() {
        Long idRole = 4L;
        Long idRestaurant = 100L;
        when(restaurantValidationCommunicationPort.isTheRestaurantOwner(idRestaurant)).thenReturn(false);
        User user = validUser();

        assertThrows(ForbiddenActionException.class, () -> userUseCaseUnderTest.saveEmployee(user, idRole, idRestaurant));

        verify(restaurantValidationCommunicationPort, times(1)).isTheRestaurantOwner(idRestaurant);
        verify(mockRoleServicePort, times(0)).getRoleById(anyLong());
        verify(passwordActionsPort, times(0)).encryptPassword(anyString());

    }

    @Test
    void saveEmployeeTest_forbiddenActionExceptionIdRoleIsNotEmployee() {
        Long idRole = 5L;
        Long idRestaurant = 100L;
        when(restaurantValidationCommunicationPort.isTheRestaurantOwner(idRestaurant)).thenReturn(true);
        User user = validUser();

        assertThrows(ForbiddenActionException.class, () -> userUseCaseUnderTest.saveEmployee(user, idRole, idRestaurant));

        verify(restaurantValidationCommunicationPort, times(1)).isTheRestaurantOwner(idRestaurant);
        verify(mockRoleServicePort, times(0)).getRoleById(anyLong());
        verify(passwordActionsPort, times(0)).encryptPassword(anyString());

    }

    @Test
    void saveEmployeeTest_idRoleNull() {
        Long idRole = null;
        Long idRestaurant = 1L;
        User user = validUser();

        assertThrows(RequiredVariableNotPresentException.class, () -> userUseCaseUnderTest.saveEmployee(user, idRole, idRestaurant));

        verify(restaurantValidationCommunicationPort, times(0)).isTheRestaurantOwner(idRestaurant);
        verify(mockRoleServicePort, times(0)).getRoleById(anyLong());
        verify(passwordActionsPort, times(0)).encryptPassword(anyString());
    }

    @Test
    void saveEmployeeTest_idRestauranteNull() {
        Long idRole = 1L;
        Long idRestaurant = null;
        User user = validUser();

        assertThrows(RequiredVariableNotPresentException.class, () -> userUseCaseUnderTest.saveEmployee(user, idRole, idRestaurant));

        verify(restaurantValidationCommunicationPort, times(0)).isTheRestaurantOwner(idRestaurant);
        verify(mockRoleServicePort, times(0)).getRoleById(anyLong());
        verify(passwordActionsPort, times(0)).encryptPassword(anyString());
    }
    @Test
    void saveClientTest_idRoleIsNotClientRoleId() {
        Long idRoleClient = 3L;
        User client = validClient();

        assertThrows(ForbiddenActionException.class, () -> userUseCaseUnderTest.saveClient(client,idRoleClient));

        verify(mockRoleServicePort, times(0)).getRoleById(idRoleClient);
        verify(passwordActionsPort, times(0)).encryptPassword(client.getPassword());
        verify(mockUserPersistencePort, times(0)).saveUser(any(User.class));
    }

    @Test
    void saveClientTest_success() {
        Long idRoleClient = 2L;
        User client = validClient();
        String passwordEncrypted = "encriptado";
        when(mockRoleServicePort.getRoleById(idRoleClient)).thenReturn(clientRole);
        when(passwordActionsPort.encryptPassword(client.getPassword())).thenReturn(passwordEncrypted);
        when(mockUserPersistencePort.saveUser(client)).thenReturn(client);

        User clientResult = userUseCaseUnderTest.saveClient(client,idRoleClient);

        verify(mockRoleServicePort, times(1)).getRoleById(idRoleClient);
        verify(passwordActionsPort, times(1)).encryptPassword(validClient().getPassword());
        verify(mockUserPersistencePort).saveUser(any(User.class));
        assertEquals(passwordEncrypted, clientResult.getPassword());
    }

    @Test
    void saveClientTest_requiredVariableIsNotPresent() {
        User user = validClient();
        user.setIdDniType(null);
        Long idRoleClient = 2L;

        assertThrows(RequiredVariableNotPresentException.class,() -> userUseCaseUnderTest.saveClient(user,idRoleClient));

        verify(mockRoleServicePort, times(0)).getRoleById(idRoleClient);
        verify(passwordActionsPort, times(0)).encryptPassword(user.getPassword());
        verify(mockUserPersistencePort, times(0)).saveUser(any(User.class));

    }

    @Test
    void saveClientTest_idRoleNull() {
        User user = validClient();
        Long idRole = null;

        assertThrows(RequiredVariableNotPresentException.class,() -> userUseCaseUnderTest.saveClient(user,idRole));

        verify(mockRoleServicePort, times(0)).getRoleById(idRole);
        verify(passwordActionsPort, times(0)).encryptPassword(user.getPassword());
        verify(mockUserPersistencePort, times(0)).saveUser(any(User.class));

    }
    @Test
    void existsRelationWithUserAndIdRestaurant_whenIdRestaurantIsNullThenThrowException() {
        String token = "token";
        Long idRestaurant = null;

        assertThrows(RequiredVariableNotPresentException.class,
                () -> userUseCaseUnderTest.existsRelationWithUserAndIdRestaurant(idRestaurant, token)
        );

        verify(tokenValidationsPort, times(1)).verifyRoleInToken(any(), any());
        verify(tokenValidationsPort, times(0)).findIdUserFromToken(any());
    }

    @Test
    void existsRelationWithUserAndIdRestaurant_whenNoRestaurantAssociated() {
        Long idRestaurant = 1L;
        String token = "token";
        Long idUserFromToken = 1L;
        User user = validUser();
        user.setIdRestaurant(null);
        when(tokenValidationsPort.findIdUserFromToken(token)).thenReturn(idUserFromToken);
        when(mockUserPersistencePort.findUserById(idUserFromToken)).thenReturn(user);

        assertThrows(NoRestaurantAssociatedWithUserException.class, () -> {
            userUseCaseUnderTest.existsRelationWithUserAndIdRestaurant(idRestaurant, token);
        });

        verify(tokenValidationsPort, times(1)).findIdUserFromToken(token);
        verify(mockUserPersistencePort, times(1)).findUserById(idUserFromToken);
    }

    @Test
    void existsRelationWithUserAndIdRestaurantWhenRelationDoesNotExist() {
        Long idRestaurant = 1L;
        String token = "token";
        Long idUserFromToken = 2L;
        User user = validUser();
        user.setId(idUserFromToken);
        user.setIdRestaurant(2L);
        when(tokenValidationsPort.findIdUserFromToken(token)).thenReturn(idUserFromToken);
        when(mockUserPersistencePort.findUserById(idUserFromToken)).thenReturn(user);

        Boolean result = userUseCaseUnderTest.existsRelationWithUserAndIdRestaurant(idRestaurant, token);

        assertFalse(result);
        verify(tokenValidationsPort, times(1)).verifyRoleInToken(token, "ROLE_EMPLOYEE");
        verify(mockUserPersistencePort, times(1)).findUserById(idUserFromToken);
    }

    @Test
    void existsRelationWithUserAndIdRestaurant_successfully() {
        Long idRestaurant = 1L;
        String token = "token";
        Long idUserFromToken = 2L;
        User user = validUser();
        user.setIdRestaurant(idRestaurant);
        when(tokenValidationsPort.findIdUserFromToken(token)).thenReturn(idUserFromToken);
        when(mockUserPersistencePort.findUserById(idUserFromToken)).thenReturn(user);

        Boolean result = userUseCaseUnderTest.existsRelationWithUserAndIdRestaurant(idRestaurant, token);

        assertTrue(result);
        verify(tokenValidationsPort, times(1)).verifyRoleInToken(token, "ROLE_EMPLOYEE");
        verify(mockUserPersistencePort, times(1)).findUserById(idUserFromToken);
    }

    @Test
    void getBasicInfoOfUserTest_successfully() {
        ArrayList<Long> userIdList = new ArrayList<>();
        userIdList.add(1L);
        userIdList.add(2L);

        String token = "test_token";

        User user1 = new User(/* user1 data */);
        User user2 = new User(/* user2 data */);

        when(mockUserPersistencePort.findUserById(1L)).thenReturn(user1);
        when(mockUserPersistencePort.findUserById(2L)).thenReturn(user2);

        // Act
        List<UserBasicInfoDto> result = userUseCaseUnderTest.getBasicInfoOfUsers(userIdList, token);

        // Assert
        assertEquals(2, result.size());
        verify(tokenValidationsPort, times(1)).verifyRoleInToken(token, ADMIN_ROLE_NAME);
        verify(mockUserPersistencePort, times(1)).findUserById(1L);
        verify(mockUserPersistencePort, times(1)).findUserById(2L);
    }
}
