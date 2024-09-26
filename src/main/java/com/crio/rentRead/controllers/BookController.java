package com.crio.rentRead.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.exceptions.*;
import com.crio.rentRead.exchanges.*;
import com.crio.rentRead.services.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(BookController.BOOK_API_ENDPOINT)
public class BookController {
    public static final String BOOK_API_ENDPOINT = "/books";
    private static final Logger logger = LoggerFactory.getLogger(BookController.class);

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> createBook(@Valid @RequestBody CreateBookRequest createBookRequest) {
        logger.info("Creating book: {}", createBookRequest);
        Book book = bookService.createBook(createBookRequest);
        logger.info("Book created: {}", book);
        return ResponseEntity.ok().body(book);
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "bookId") int bookId) throws BookNotFoundException {
        logger.info("Fetching book with ID: {}", bookId);
        Book book = bookService.findBookById(bookId);
        logger.info("Fetched book: {}", book);
        return ResponseEntity.ok().body(book);
    }

    @PostMapping("/{bookId}/rent")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<RentBookResponse> rentBook(@PathVariable(value = "bookId") int bookId, 
                                                     @AuthenticationPrincipal UserDetails userDetails) 
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, RentalException {
        logger.info("User {} is attempting to rent book with ID: {}", userDetails.getUsername(), bookId);
        RentBookResponse rentBookResponse = bookService.rentBook(bookId, userDetails);
        logger.info("User {} successfully rented book with ID: {}", userDetails.getUsername(), bookId);
        return ResponseEntity.ok().body(rentBookResponse);
    }

    @PostMapping("/{bookId}/return")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ReturnBookResponse> returnBook(@PathVariable(value = "bookId") int bookId, 
                                                         @AuthenticationPrincipal UserDetails userDetails) 
            throws UserNotFoundException, BookNotFoundException, BookNotRentedException {
        logger.info("User {} is attempting to return book with ID: {}", userDetails.getUsername(), bookId);
        ReturnBookResponse returnBookResponse = bookService.returnBook(bookId, userDetails);
        logger.info("User {} successfully returned book with ID: {}", userDetails.getUsername(), bookId);
        return ResponseEntity.ok().body(returnBookResponse);
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<GetAllBooksResponse> getAllBooks() {
        logger.info("Fetching all books");
        GetAllBooksResponse getAllBooksResponse = bookService.findAllBooks();
        logger.info("Fetched {} books", getAllBooksResponse.getBooks().size());
        return ResponseEntity.ok().body(getAllBooksResponse);
    }

    @PutMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "bookId") int bookId, 
                                            @Valid @RequestBody UpdateBookRequest updateBookRequest) 
            throws BookNotFoundException {
        logger.info("Updating book with ID: {} with data: {}", bookId, updateBookRequest);
        Book book = bookService.updateBook(bookId, updateBookRequest);
        logger.info("Book updated: {}", book);
        return ResponseEntity.ok().body(book);
    }

    @DeleteMapping("/{bookId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteBook(@PathVariable(value = "bookId") int bookId) 
            throws BookNotFoundException {
        logger.info("Deleting book with ID: {}", bookId);
        String response = bookService.deleteBook(bookId);
        logger.info("Deleted book with ID: {}", bookId);
        return ResponseEntity.ok().body(response);
    }
}
