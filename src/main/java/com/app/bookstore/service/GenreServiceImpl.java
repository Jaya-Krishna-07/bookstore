package com.app.bookstore.service;

import com.app.bookstore.entity.Genre;
import com.app.bookstore.exception.ResourceNotFoundException;
import com.app.bookstore.repository.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService{
    private final GenreRepository genreRepository;
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Genre getGenreById(Long genreId) {
        return genreRepository.findById(genreId)
                .orElseThrow(() -> { throw new ResourceNotFoundException("genre not found with id: " + genreId);
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
                            throw new ResourceNotFoundException("genre not found with id: " + genreId);
                        }
                );
        existingGenre.setName(genre.getName());
        return genreRepository.save(existingGenre);
    }

    @Override
    public String deleteGenre(Long genreId) {
        if (genreRepository.findById(genreId).isPresent()){
            genreRepository.deleteById(genreId);
            return "successfully deleted";
        }
        return "unsuccessfull";
    }
}
