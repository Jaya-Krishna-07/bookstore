package com.app.bookstore.service;

import com.app.bookstore.entity.Genre;
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
    public Genre createGenre(Genre genre) {
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
        Optional<Genre> genreById = genreRepository.findById(genreId);
        if (genreById.isEmpty()) {
            throw new NotFoundException("genre with id: " + genreById + " does not exist!");
        }
        genreRepository.deleteById(genreId);
        return "successful";
    }

    @Override
    public Genre getGenreByName(String genreName) {
        return genreRepository.findByName(genreName);
    }

    @Override
    public String deleteGenreByName(String genreName) {
        System.out.println("inside the service method deleteGenreByName(String genreName)");
        Genre genreByName = genreRepository.findByName(genreName);
        if (genreByName == null) {
            throw new NotFoundException("Genre not found with the name: " + genreName);
        }
        return deleteGenre(genreByName.getId());
    }


}
