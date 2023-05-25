package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoleUseCaseTest {

    private IRolePersistencePort rolePersistencePort;
    private RoleUseCase roleUseCase;

    @BeforeEach
    void setUp() {
        rolePersistencePort = mock(IRolePersistencePort.class);
        roleUseCase = new RoleUseCase(rolePersistencePort);
    }

    @Test
    void getRoleByIdTest_whenIdNotFound() {
        Long id = 1L;
        when(rolePersistencePort.getRoleById(id)).thenReturn(null);

        Role role = roleUseCase.getRoleById(id);

        verify(rolePersistencePort, times(1)).getRoleById(id);
        assertEquals(null, role);
    }

    @Test
    @DisplayName("Should return the role when the id is valid")
    void getRoleByIdTest_whenIdIsValid() {
        Long roleId = 1L;
        Role expectedRole = new Role(roleId, "Admin", "Admin role");

        when(rolePersistencePort.getRoleById(roleId)).thenReturn(expectedRole);

        Role actualRole = roleUseCase.getRoleById(roleId);

        assertEquals(expectedRole, actualRole);
        verify(rolePersistencePort, times(1)).getRoleById(roleId);
    }

    @Test
    void getAllRolesTest_returnAllRoles() {
        List<Role> expectedRoles = new ArrayList<>();
        expectedRoles.add(new Role(1L, "Admin", "Admin role"));
        expectedRoles.add(new Role(2L, "User", "User role"));
        when(rolePersistencePort.getAllRoles()).thenReturn(expectedRoles);

        List<Role> actualRoles = roleUseCase.getAllRoles();

        assertEquals(expectedRoles.size(), actualRoles.size());
        for (int i = 0; i < expectedRoles.size(); i++) {
            Role expectedRole = expectedRoles.get(i);
            Role actualRole = actualRoles.get(i);
            assertEquals(expectedRole.getId(), actualRole.getId());
            assertEquals(expectedRole.getName(), actualRole.getName());
            assertEquals(expectedRole.getDescription(), actualRole.getDescription());
        }

        verify(rolePersistencePort, times(1)).getAllRoles();
    }
}