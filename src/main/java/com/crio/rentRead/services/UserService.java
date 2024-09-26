package com.crio.rentRead.services;

import com.crio.rentRead.dto.User;
import com.crio.rentRead.exceptions.InvalidCredentialsException;
import com.crio.rentRead.exceptions.UserNotRegisteredException;
import com.crio.rentRead.exchanges.LoginUserRequest;
import com.crio.rentRead.exchanges.RegisterUserRequest;

public interface UserService {
    
    User registerUser(RegisterUserRequest registerUserRequest);

    String loginUser(LoginUserRequest loginUserRequest) throws InvalidCredentialsException, UserNotRegisteredException;

}
