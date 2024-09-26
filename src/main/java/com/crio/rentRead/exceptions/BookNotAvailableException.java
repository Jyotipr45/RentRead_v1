package com.crio.rentRead.exceptions;

import java.io.IOException;

public class BookNotAvailableException extends IOException {

    public BookNotAvailableException(String message) {
        super(message);
    }
    
}
