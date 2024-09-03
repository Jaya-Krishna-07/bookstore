package com.app.bookstore.controller;

import com.app.bookstore.entity.Book;
import com.app.bookstore.exception.AlreadyExistsException;
import com.app.bookstore.request.CreateBook;
import com.app.bookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/books")
@RequiredArgsConstructor
@RestController
public class BookController {
    private final BookService bookService;

    @GetMapping("/greet")
    public String greeting() {
        return "hello there!";
    }

    @PostMapping
    public ResponseEntity<?> createBook(@RequestBody CreateBook createBookRequest) {
        try {
            Book createdBook = bookService.createBook(createBookRequest);
            return ResponseEntity.ok(createdBook);
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);
        return new ResponseEntity<>(book, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        return new ResponseEntity<>(allBooks, HttpStatus.OK);
    }
    @GetMapping("/byGenre/{genreName}")
    public ResponseEntity<List<Book>> getBooksByGenre(@PathVariable String genreName) {
        List<Book> booksByGenre = bookService.getBooksByGenre(genreName);
        return ResponseEntity.ok().body(booksByGenre);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        Book updatedBook = bookService.updateBook(bookId, book);
        return new ResponseEntity<>(updatedBook, HttpStatus.OK);
    }


    @DeleteMapping("/{bookId}")
    public ResponseEntity<String> deleteBook(@PathVariable Long bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>("book was deleted successfully", HttpStatus.OK);
    }



}
