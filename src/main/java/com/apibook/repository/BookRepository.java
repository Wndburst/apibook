package com.apibook.repository;

import com.apibook.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer> {
    List<Book> findByType(String type);
    Optional<Book> findByTitle(String title);
}


