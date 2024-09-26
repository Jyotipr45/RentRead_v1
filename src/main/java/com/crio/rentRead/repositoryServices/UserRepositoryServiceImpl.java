package com.crio.rentRead.repositoryServices;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.mapper.Mapper;
import com.crio.rentRead.models.RoleEntity;
import com.crio.rentRead.models.UserEntity;
import com.crio.rentRead.repositories.RoleRepository;
import com.crio.rentRead.repositories.UserRepository;

@Service
public class UserRepositoryServiceImpl implements UserRepositoryService {

    private static final Logger logger = LoggerFactory.getLogger(UserRepositoryServiceImpl.class);
    
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(String firstName, String lastName, String email, String password, String role) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);

        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(firstName);
        userEntity.setLastName(lastName);
        userEntity.setEmail(email);
        userEntity.setPassword(encodedPassword);
        RoleEntity roleEntity = findOrCreateRole(role);
        userEntity.setRole(roleEntity);

        userRepository.save(userEntity);
        User user = Mapper.mapToUser(userEntity);
        
        logger.info("Registered user: {}", user);
        return user;
    }

    @Override
    public User saveUser(User user) {
        UserEntity userEntity = Mapper.mapToUserEntity(user);
        User savedUser = Mapper.mapToUser(userRepository.save(userEntity));
        logger.info("Saved user: {}", savedUser);
        return savedUser;
    }

    @Override
    public User findUserById(int userId) throws UserNotFoundException {
        String message = "Could not find user with ID: " + userId;
        UserEntity userEntity = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(message));
        User user = Mapper.mapToUser(userEntity);
        logger.info("Found user: {}", user);
        return user;
    }

    @Override
    public User findUserByEmail(String email) throws UserNotFoundException {
        String message = "Could not find user with email: " + email;
        UserEntity userEntity = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException(message));
        User user = Mapper.mapToUser(userEntity);
        logger.info("Found user by email: {}", user);
        return user;
    }

    private RoleEntity findOrCreateRole(String roleName) {
        Optional<RoleEntity> maybeRoleEntity = roleRepository.findByName(roleName);

        if (maybeRoleEntity.isPresent()) {
            logger.info("Role found: {}", roleName);
            return maybeRoleEntity.get();
        } else {
            RoleEntity roleEntity = new RoleEntity();
            roleEntity.setName(roleName);
            roleRepository.save(roleEntity);
            logger.info("Created new role: {}", roleName);
            return roleEntity;
        }
    }
}
