package com.crio.rentRead.services;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.dto.User;
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
import com.crio.rentRead.repositoryServices.BookRepositoryService;
import com.crio.rentRead.repositoryServices.UserRepositoryService;

@Service
public class BookServiceImpl implements BookService {
    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private UserRepositoryService userRepositoryService;

    @Autowired
    private BookRepositoryService bookRepositoryService;

    @Override
    public Book createBook(CreateBookRequest createBookRequest) {
        logger.info("Creating book with request: {}", createBookRequest);
        Book book = bookRepositoryService.createBook(createBookRequest.getTitle(), 
                                                       createBookRequest.getAuthor(), 
                                                       createBookRequest.getGenre(), 
                                                       createBookRequest.getAvailabilityStatus().toUpperCase());
        logger.info("Book created: {}", book);
        return book;
    }

    @Override
    public RentBookResponse rentBook(int bookId, UserDetails userDetails) 
            throws UserNotFoundException, BookNotFoundException, BookNotAvailableException, RentalException {
        logger.info("User {} is attempting to rent book with ID: {}", userDetails.getUsername(), bookId);
        User user = userRepositoryService.findUserByEmail(userDetails.getUsername());
        Book book = bookRepositoryService.findBookById(bookId);
        
        checkRentingEligibility(user, book);

        Set<Book> rentedBooks = user.getRentedBooks();
        rentedBooks.add(book);
        book.setAvailabilityStatus("NOT AVAILABLE");

        bookRepositoryService.saveBook(book);
        user = userRepositoryService.saveUser(user);

        RentBookResponse rentBookResponse = new RentBookResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getRole(), user.getRentedBooks());
        logger.info("User {} successfully rented book with ID: {}", userDetails.getUsername(), bookId);
        return rentBookResponse;
    }

    @Override
    public ReturnBookResponse returnBook(int bookId, UserDetails userDetails) 
            throws UserNotFoundException, BookNotFoundException, BookNotRentedException {
        logger.info("User {} is attempting to return book with ID: {}", userDetails.getUsername(), bookId);
        User user = userRepositoryService.findUserByEmail(userDetails.getUsername());
        Book book = bookRepositoryService.findBookById(bookId);

        if (hasRentedBook(user, book)) {
            Set<Book> rentedBooks = user.getRentedBooks();
            rentedBooks.remove(book);
            book.setAvailabilityStatus("AVAILABLE");
            user = userRepositoryService.saveUser(user);
            bookRepositoryService.saveBook(book);
            ReturnBookResponse returnBookResponse = new ReturnBookResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getRole(), user.getRentedBooks());
            logger.info("User {} successfully returned book with ID: {}", userDetails.getUsername(), bookId);
            return returnBookResponse;
        } else {
            logger.error("User {} tried to return a book they did not rent: {}", userDetails.getUsername(), bookId);
            throw new BookNotRentedException("Book not rented");
        }
    }

    @Override
    public Book updateBook(int bookId, UpdateBookRequest updateBookRequest) throws BookNotFoundException {
        logger.info("Updating book with ID: {} with data: {}", bookId, updateBookRequest);
        Book book = bookRepositoryService.updateBook(bookId, updateBookRequest.getTitle(), 
                                                       updateBookRequest.getAuthor(), 
                                                       updateBookRequest.getGenre(), 
                                                       updateBookRequest.getAvailabilityStatus().toUpperCase());
        logger.info("Book updated: {}", book);
        return book;
    }

    @Override
    public GetAllBooksResponse findAllBooks() {
        logger.info("Fetching all books");
        List<Book> books = bookRepositoryService.findAllBooks();
        GetAllBooksResponse getAllBooksResponse = new GetAllBooksResponse(books);
        logger.info("Fetched {} books", getAllBooksResponse.getBooks().size());
        return getAllBooksResponse;
    }

    @Override
    public String deleteBook(int bookId) throws BookNotFoundException {
        logger.info("Deleting book with ID: {}", bookId);
        String response = "Successfully deleted book with ID: " + bookId;
        bookRepositoryService.deleteBook(bookId);
        logger.info("Deleted book with ID: {}", bookId);
        return response;
    }

    private void checkRentingEligibility(User user, Book book) throws BookNotAvailableException, RentalException {
        String availabilityStatus = book.getAvailabilityStatus();
        Set<Book> rentedBooks = user.getRentedBooks();
    
        logger.info("Checking renting eligibility for user: {} with rented books: {}", user.getEmail(), rentedBooks.size());
        logger.info("Book availability status: {}", availabilityStatus);
    
        if (availabilityStatus.equals("NOT AVAILABLE")) {
            logger.error("Book is not available for rent: {}", book.getTitle());
            throw new BookNotAvailableException("Book is not available for rent");
        }
    
        if (rentedBooks.size() >= 2) {
            logger.error("User has reached the rental limit. Current rented books: {}", rentedBooks.size());
            throw new RentalException("User already has two active rentals");
        }
    
        logger.info("User is eligible to rent the book: {}", book.getTitle());
    }
    

    private boolean hasRentedBook(User user, Book book) {
        Set<Book> rentedBooks = user.getRentedBooks();
        
        logger.info("Checking if user: {} has rented book: {}", user.getEmail(), book.getTitle());
    
        boolean hasRented = rentedBooks.contains(book);
        
        if (hasRented) {
            logger.info("User: {} has rented the book: {}", user.getEmail(), book.getTitle());
        } else {
            logger.info("User: {} has not rented the book: {}", user.getEmail(), book.getTitle());
        }
    
        return hasRented;
    }
    

    @Override
    public Book findBookById(int bookId) throws BookNotFoundException {
        logger.info("Fetching book with ID: {}", bookId);
        Book book = bookRepositoryService.findBookById(bookId);
        
        if (book == null) {
            logger.error("Book not found with ID: {}", bookId);
            throw new BookNotFoundException("Book not found with ID: " + bookId);
        }
        
        logger.info("Fetched book: {}", book);
        return book;
    }

    
}
