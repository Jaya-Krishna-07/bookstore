package com.app.bookstore.service;

import com.app.bookstore.entity.Book;
import com.app.bookstore.request.CreateBook;

import java.util.List;

public interface BookService {
    Book createBook(CreateBook book);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book updateBook(Long bookId, Book book);
    void deleteBook(Long bookId);

    List<Book> getBooksByGenre(String genreName);
}
