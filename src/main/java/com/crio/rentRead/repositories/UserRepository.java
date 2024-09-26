package com.crio.rentRead.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.rentRead.models.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
}
