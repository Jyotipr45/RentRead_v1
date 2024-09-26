package com.crio.rentRead.exchanges;

import java.util.List;

import com.crio.rentRead.dto.Book;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GetAllBooksResponse {
    
    private List<Book> books;
    
}
