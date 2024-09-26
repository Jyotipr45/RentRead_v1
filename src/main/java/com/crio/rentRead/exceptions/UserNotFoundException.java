package com.crio.rentRead.exceptions;

import java.io.IOException;

public class UserNotFoundException extends IOException {
    
    public UserNotFoundException(String message) {
        super(message);
    }
    
}
