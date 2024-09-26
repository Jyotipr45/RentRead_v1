package com.crio.rentRead.exceptions;

import java.io.IOException;

public class BookNotRentedException extends IOException {
    
    public BookNotRentedException(String message) {
        super(message);
    }
}
