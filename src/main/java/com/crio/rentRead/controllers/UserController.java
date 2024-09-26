package com.crio.rentRead.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.InvalidCredentialsException;
import com.crio.rentRead.exceptions.UserNotRegisteredException;
import com.crio.rentRead.exchanges.LoginUserRequest;
import com.crio.rentRead.exchanges.RegisterUserRequest;
import com.crio.rentRead.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(UserController.USER_API_ENDPOINT)
public class UserController {
    public static final String USER_API_ENDPOINT = "/users";
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        logger.info("Registering user: {}", registerUserRequest);
        User registeredUser = userService.registerUser(registerUserRequest);
        logger.info("User registered: {}", registeredUser);
        return ResponseEntity.ok().body(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody LoginUserRequest loginUserRequest) 
            throws InvalidCredentialsException, UserNotRegisteredException {
        logger.info("User login attempt: {}", loginUserRequest.getEmail());
        String response = userService.loginUser(loginUserRequest);
        logger.info("Login successful for user: {}", loginUserRequest.getEmail());
        return ResponseEntity.ok().body(response);
    }
}
