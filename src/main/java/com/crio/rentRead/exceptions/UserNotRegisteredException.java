package com.crio.rentRead.exceptions;

import java.io.IOException;

public class UserNotRegisteredException extends IOException {

    public UserNotRegisteredException(String message) {
        super(message);
    }
    
}
