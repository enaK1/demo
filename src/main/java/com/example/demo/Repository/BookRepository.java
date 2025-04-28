package com.example.demo.Repository;

import com.example.demo.Entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByTitleContainingIgnoreCase(String title);

    List<Book> findByAuthorContainingIgnoreCase(String author);

    List<Book> findByIsCheckedOut(boolean isCheckedOut);

    List<Book> findByTitleContainingOrAuthorContaining(String title, String author);

    List<Book> findByIsCheckedOutFalse();
}
