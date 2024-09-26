package com.crio.rentRead.services;

import org.springframework.security.core.userdetails.UserDetails;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.exceptions.BookNotAvailableException;
import com.crio.rentRead.exceptions.BookNotFoundException;
import com.crio.rentRead.exceptions.BookNotRentedException;
import com.crio.rentRead.exceptions.RentalException;
import com.crio.rentRead.exceptions.UserNotFoundException;
import com.crio.rentRead.exchanges.CreateBookRequest;
import com.crio.rentRead.exchanges.GetAllBooksResponse;
import com.crio.rentRead.exchanges.RentBookResponse;
import com.crio.rentRead.exchanges.ReturnBookResponse;
import com.crio.rentRead.exchanges.UpdateBookRequest;

public interface BookService {

    Book createBook(CreateBookRequest createBookRequest);

    RentBookResponse rentBook(int bookId, UserDetails userDetails) throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, RentalException;

    ReturnBookResponse returnBook(int bookId, UserDetails userDetails) throws UserNotFoundException, BookNotFoundException, BookNotRentedException;

    Book updateBook(int bookId, UpdateBookRequest updateBookRequest) throws BookNotFoundException;

    GetAllBooksResponse findAllBooks();

    Book findBookById(int bookId) throws BookNotFoundException;

    String deleteBook(int bookId) throws BookNotFoundException;
    
}
