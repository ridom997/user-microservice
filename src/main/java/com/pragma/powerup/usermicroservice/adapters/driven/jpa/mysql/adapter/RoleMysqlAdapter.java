package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.adapter;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.RoleEntity;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.mappers.IRoleEntityMapper;
import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories.IRoleRepository;
import com.pragma.powerup.usermicroservice.domain.model.Role;
import com.pragma.powerup.usermicroservice.domain.spi.IRolePersistencePort;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class RoleMysqlAdapter implements IRolePersistencePort {
    private final IRoleRepository roleRepository;
    private final IRoleEntityMapper roleEntityMapper;
    @Override
    public List<Role> getAllRoles() {
        List<RoleEntity> roleEntityList = roleRepository.findAll();
        return roleEntityMapper.toRoleList(roleEntityList);
    }

    @Override
    public Role getRoleById(Long id) {
        Optional<RoleEntity> roleEntity = roleRepository.findById(id);
        if (!roleEntity.isPresent()) return null;
        return roleEntityMapper.toRole(roleEntity.get());
    }
}
