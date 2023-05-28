package com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.repositories;

import com.pragma.powerup.usermicroservice.adapters.driven.jpa.mysql.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByDniNumberAndIdDniType(String dniNumber, String idDniType);
    Optional<UserEntity> findByDniNumber(String dniNumber);

    Boolean existsByDniNumber(String dniNumber);

    boolean existsByMail(String mail);

    Optional<UserEntity> findByMail(String mail);

}
