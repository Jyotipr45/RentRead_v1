package com.crio.rentRead.repositoryServices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crio.rentRead.dto.Book;
import com.crio.rentRead.exceptions.BookNotFoundException;
import com.crio.rentRead.mapper.Mapper;
import com.crio.rentRead.models.BookEntity;
import com.crio.rentRead.repositories.BookRepository;

@Service
public class BookRepositoryServiceImpl implements BookRepositoryService {

    private static final Logger logger = LoggerFactory.getLogger(BookRepositoryServiceImpl.class);

    @Autowired
    private BookRepository bookRepository; 

    @Override
    public Book createBook(String title, String author, String genre, String availabilityStatus) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setTitle(title);
        bookEntity.setAuthor(author);
        bookEntity.setGenre(genre);
        bookEntity.setAvailabilityStatus(availabilityStatus);

        Book book = Mapper.mapToBook(bookRepository.save(bookEntity));
        logger.info("Created book: {}", book);
        return book;
    }

    @Override
    public Book saveBook(Book book) {
        BookEntity bookEntity = Mapper.mapToBookEntity(book);
        Book savedBook = Mapper.mapToBook(bookRepository.save(bookEntity));
        logger.info("Saved book: {}", savedBook);
        return savedBook;
    }

    @Override
    public Book findBookById(int bookId) throws BookNotFoundException {
        String message = "Could not find book with ID: " + bookId;
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(message));
        Book book = Mapper.mapToBook(bookEntity);
        logger.info("Found book: {}", book);
        return book;
    }

    @Override
    public Book updateBook(int bookId, String title, String author, String genre, String availabilityStatus) throws BookNotFoundException {
        String message = "Could not find book with ID: " + bookId;
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(message));
        
        bookEntity.setTitle(title);
        bookEntity.setAuthor(author);
        bookEntity.setGenre(genre);
        bookEntity.setAvailabilityStatus(availabilityStatus);

        Book updatedBook = Mapper.mapToBook(bookRepository.save(bookEntity));
        logger.info("Updated book: {}", updatedBook);
        return updatedBook;
    }

    @Override
    public List<Book> findAllBooks() {
        List<BookEntity> bookEntities = bookRepository.findAll();
        List<Book> books = Mapper.mapToBookList(bookEntities);
        logger.info("Retrieved all books: {}", books.size());
        return books;
    }

    @Override
    public void deleteBook(int bookId) throws BookNotFoundException {
        String message = "Could not find book with ID: " + bookId;
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(message));
        bookRepository.delete(bookEntity);
        logger.info("Deleted book with ID: {}", bookId);
    }
}
