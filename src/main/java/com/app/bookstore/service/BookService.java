package com.app.bookstore.service;

import com.app.bookstore.entity.Book;

import java.util.List;

public interface BookService {
    Book createBook(Book book);
    Book getBookById(Long bookId);
    List<Book> getAllBooks();
    Book updateBook(Long bookId, Book book);
    void deleteBook(Long bookId);
}
