package com.crio.rentRead.dto;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private int id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private String role;

    private Set<Book> rentedBooks;

}
