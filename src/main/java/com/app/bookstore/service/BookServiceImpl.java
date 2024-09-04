package com.app.bookstore.service;

import com.app.bookstore.entity.Book;
import com.app.bookstore.entity.Genre;
import com.app.bookstore.exception.AlreadyExistsException;
import com.app.bookstore.exception.NotFoundException;
import com.app.bookstore.repository.BookRepository;
import com.app.bookstore.request.CreateBook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService{
    private final BookRepository bookRepository;
    private final GenreService genreService;
    @Override
    public Book createBook(CreateBook createBookRequest) {
//        search for an existing genre using the name
        Genre genre = genreService.getGenreByName(createBookRequest.getGenre());
//        if genre does not exist, create it
        if (genre == null){
            genre = new Genre();
            genre.setName(createBookRequest.getGenre());
            genre = genreService.createGenre(genre);
        }
//        check if a book with the given title already exists, and throw an error if it exists because titles are unique
        Book book = bookRepository.findByTitle(createBookRequest.getTitle());
        if (book != null) {
            throw new AlreadyExistsException("book with the title: " + createBookRequest.getTitle() + " already exists!");
        }
//        create a book
        book = new Book();
        book.setTitle(createBookRequest.getTitle());
        book.setAuthor(createBookRequest.getAuthor());
        book.setDescription(createBookRequest.getDescription());
        book.setGenre(genre);
        return bookRepository.save(book);
    }

    @Override
    public Book getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> {
                    throw new NotFoundException("book not found with id: " + bookId);
                });
        return book;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book updateBook(Long bookId, Book book) {
        // Check if the book with bookId exists
        Book existingBook = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book not found with id " + bookId));

        // Update the existing book details
        existingBook.setTitle(book.getTitle());
        existingBook.setAuthor(book.getAuthor());
        existingBook.setDescription(book.getDescription());
        existingBook.setGenre(book.getGenre());

        // Save the updated book
        return bookRepository.save(existingBook);
    }


    @Override
    public void deleteBook(Long bookId) {
        Optional<Book> bookById = bookRepository.findById(bookId);
        if (bookById.isEmpty()) {
            throw new NotFoundException("book with the id: " + bookById + " does not exist");
        }
        
        bookRepository.deleteById(bookId);
    }

    @Override
    public List<Book> getBooksByGenre(String genreName) {
        List<Book> booksByGenreName = bookRepository.findBooksByGenreName(genreName);
        return booksByGenreName;
    }
}
