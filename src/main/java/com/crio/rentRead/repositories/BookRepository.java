package com.crio.rentRead.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crio.rentRead.models.BookEntity;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
    
}
