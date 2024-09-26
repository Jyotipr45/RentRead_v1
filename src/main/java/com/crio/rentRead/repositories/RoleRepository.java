package com.crio.rentRead.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.rentRead.models.RoleEntity;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

    Optional<RoleEntity> findByName(String name);
    
}
