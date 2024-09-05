package com.app.bookstore.service;

import com.app.bookstore.entity.Genre;
import com.app.bookstore.exception.AlreadyExistsException;
import com.app.bookstore.exception.NotFoundException;
import com.app.bookstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    throw new NotFoundException("genre not found with id: " + genreId);
                });
    }

    @Override
    public Genre getGenreByName(String genreName) {
        Genre genre = genreRepository.findByName(genreName)
                .orElseThrow(() -> {
                    throw new NotFoundException("genre not found with name: " + genreName);
                });
        if (genre == null) {
            throw new NotFoundException("genre not found with name: " + genreName);
        }
        return genre;
    }

    @Override
    public Genre createGenre(Genre genre) {
        Optional<Genre> genreByName = genreRepository.findByName(genre.getName());
        if (genreByName.isPresent()) {
            throw new AlreadyExistsException("genre already exists with name: " + genre.getName());
        }
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(Long genreId, Genre genre) {
        Genre existingGenre = genreRepository.findById(genreId)
                .orElseThrow(() -> {
                            throw new NotFoundException("genre not found with id: " + genreId);
                        }
                );
        existingGenre.setName(genre.getName());
        return genreRepository.save(existingGenre);
    }


    @Override
    public String deleteGenre(Long genreId) {
        System.out.println("inside deleteGenre(Long genreId)");
        Genre genreById = genreRepository.findById(genreId)
                .orElseThrow(() -> {
                    throw new NotFoundException("genre not found with id: " + genreId);
                });
        genreRepository.deleteById(genreId);
        return "successful";
    }

    @Override
    public String deleteGenreByName(String genreName) {
        System.out.println("inside the service method deleteGenreByName(String genreName)");
        Genre genreByName = genreRepository.findByName(genreName)
                .orElseThrow(() -> {
                    throw new NotFoundException("genre not found with name: " + genreName);
                });
        return deleteGenre(genreByName.getId());
    }


}
