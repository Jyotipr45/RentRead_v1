package com.crio.rentRead.repositoryServices;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.UserNotFoundException;

public interface UserRepositoryService {
    
    User registerUser(String firstName, String lastName, String email, String password, String role);

    User saveUser(User user);

    User findUserById(int userId) throws UserNotFoundException;

    User findUserByEmail(String email) throws UserNotFoundException;
    
}
