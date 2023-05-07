package com.pragma.powerup.usermicroservice.domain.spi;

import com.pragma.powerup.usermicroservice.domain.model.Role;

import java.util.List;
import java.util.Optional;

public interface IRolePersistencePort {
    List<Role> getAllRoles();
    Role getRoleById(Long id);
}
