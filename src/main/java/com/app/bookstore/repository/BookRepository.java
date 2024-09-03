package com.app.bookstore.repository;

import com.app.bookstore.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("SELECT b FROM Book b WHERE b.genre.name= :genreName")
    List<Book> findBooksByGenreName(@Param("genreName") String genreName);
}
