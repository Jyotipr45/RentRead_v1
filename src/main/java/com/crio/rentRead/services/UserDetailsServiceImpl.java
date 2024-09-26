package com.crio.rentRead.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.dto.UserDetailsDto;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.repositoryServices.UserRepositoryService;

public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);
    private UserRepositoryService userRepositoryService;

    public UserDetailsServiceImpl(UserRepositoryService userRepositoryService) {
        this.userRepositoryService = userRepositoryService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepositoryService.findUserByEmail(username);
            logger.info("Loaded user: {}", user);
            return new UserDetailsDto(user);
        } catch (UserNotFoundException e) {
            logger.error("User not found with email: {}", username);
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
    }
}
