package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.model.User;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import com.pragma.powerup.usermicroservice.domain.spi.IUserPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testSaveOwner() {
        // Setup
        final User user = new User(0L, "name", "surname", "mail", "phone", "address", "idDniType", "dniNumber",
                "idPersonType", "password", "birthday", new Role(0L, "name", "description"));
        when(mockRolePersistencePort.getRoleById(0L)).thenReturn(new Role(0L, "name", "description"));

        // Run the test
        userUseCaseUnderTest.saveOwner(user);

        // Verify the results
        verify(mockPersonPersistencePort).saveUser(any(User.class));
    }
}
