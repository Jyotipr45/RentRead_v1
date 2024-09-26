package com.crio.rentRead.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.InvalidCredentialsException;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.exceptions.UserNotRegisteredException;
import com.crio.rentRead.exchanges.LoginUserRequest;
import com.crio.rentRead.exchanges.RegisterUserRequest;
import com.crio.rentRead.repositoryServices.UserRepositoryService;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(RegisterUserRequest registerUserRequest) {
        logger.info("Registering user: {}", registerUserRequest);
        User user = userRepositoryService.registerUser(
            registerUserRequest.getFirstName(),
            registerUserRequest.getLastName(),
            registerUserRequest.getEmail(),
            registerUserRequest.getPassword(),
            registerUserRequest.getRole().toUpperCase()
        );
        logger.info("User registered: {}", user);
        return user;
    }

    @Override
    public String loginUser(LoginUserRequest loginUserRequest) throws InvalidCredentialsException, UserNotRegisteredException {
        logger.info("User login attempt: {}", loginUserRequest.getEmail());
        User user;

        try {
            user = userRepositoryService.findUserByEmail(loginUserRequest.getEmail());
        } catch (UserNotFoundException e) {
            logger.error("User not found: {}", loginUserRequest.getEmail());
            throw new UserNotRegisteredException("User not registered");
        }

        if (isPasswordMatching(loginUserRequest.getPassword(), user.getPassword())) {
            logger.info("Login successful for user: {}", loginUserRequest.getEmail());
            return "Login successful";
        } else {
            logger.error("Invalid credentials for user: {}", loginUserRequest.getEmail());
            throw new InvalidCredentialsException("Invalid email or password");
        }
    }

    private boolean isPasswordMatching(String actualPassword, String expectedPassword) {
        boolean isMatch = bCryptPasswordEncoder.matches(actualPassword, expectedPassword);
        
        if (isMatch) {
            logger.info("Password match successful.");
        } else {
            logger.warn("Password match failed for provided credentials.");
        }
        
        return isMatch;
    }
    
}
