package com.crio.rentRead.globalExceptionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crio.rentRead.exceptions.BookNotAvailableException;
import com.crio.rentRead.exceptions.BookNotFoundException;
import com.crio.rentRead.exceptions.BookNotRentedException;
import com.crio.rentRead.exceptions.InvalidCredentialsException;
import com.crio.rentRead.exceptions.RentalException;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.exceptions.UserNotRegisteredException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
        logger.error("User not found: {}", ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
        logger.error("Book not found: {}", ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        logger.warn("Invalid credentials: {}", ex.getMessage());
        return ResponseEntity.status(401).body(ex.getMessage());
    }

    @ExceptionHandler(UserNotRegisteredException.class)
    public ResponseEntity<String> handleUserNotRegisteredException(UserNotRegisteredException ex) {
        logger.error("User not registered: {}", ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }

    @ExceptionHandler(BookNotAvailableException.class)
    public ResponseEntity<String> handleBookNotAvailableException(BookNotAvailableException ex) {
        logger.warn("Book not available: {}", ex.getMessage());
        return ResponseEntity.status(400).body(ex.getMessage());
    }
    
    @ExceptionHandler(RentalException.class)
    public ResponseEntity<String> handleRentalException(RentalException ex) {
        logger.error("Rental error: {}", ex.getMessage());
        return ResponseEntity.status(400).body(ex.getMessage());
    }

    @ExceptionHandler(BookNotRentedException.class)
    public ResponseEntity<String> handleBookNotRentedException(BookNotRentedException ex) {
        logger.error("Book not rented: {}", ex.getMessage());
        return ResponseEntity.status(404).body(ex.getMessage());
    }
}
