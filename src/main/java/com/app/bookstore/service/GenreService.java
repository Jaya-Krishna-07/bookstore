package com.app.bookstore.service;

import com.app.bookstore.entity.Book;
import com.app.bookstore.entity.Genre;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface GenreService {
    List<Genre> getAllGenres();

    Genre getGenreById(Long genreId);

    Genre createGenre(Genre genre);

    Genre updateGenre(Long genreId, Genre genre);

    String deleteGenre(Long genreId);

    Genre getGenreByName(String genreName);
}
