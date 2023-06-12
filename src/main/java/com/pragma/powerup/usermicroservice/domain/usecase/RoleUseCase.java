package com.pragma.powerup.usermicroservice.domain.usecase;

import com.pragma.powerup.usermicroservice.domain.api.IRoleServicePort;
import com.pragma.powerup.usermicroservice.domain.exceptions.RequiredVariableNotPresentException;
import com.pragma.powerup.usermicroservice.domain.exceptions.RoleNotFoundException;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;

import java.util.List;

public class RoleUseCase implements IRoleServicePort {

    private final IRolePersistencePort rolePersistencePort;

    public RoleUseCase(IRolePersistencePort rolePersistencePort) {
        this.rolePersistencePort = rolePersistencePort;
    }

    @Override
    public List<Role> getAllRoles() {
        return rolePersistencePort.getAllRoles();
    }

    @Override
    public Role getRoleById(Long id) {
        if(id == null) throw new RequiredVariableNotPresentException("idRole not present");
        Role role = rolePersistencePort.getRoleById(id);
        if (role == null) throw new RoleNotFoundException();
        return role;
    }

}
