package com.crio.rentRead.exchanges;

import java.util.Set;

import com.crio.rentRead.dto.Book;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentBookResponse {

    private int id;

    private String firstName;

    private String lastName;

    private String role;

    private Set<Book> rentedBooks;

}
