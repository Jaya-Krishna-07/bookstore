package com.app.bookstore.controller;

import com.app.bookstore.entity.Genre;
import com.app.bookstore.exception.NotFoundException;
import com.app.bookstore.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genre")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    @GetMapping
    public ResponseEntity<List<Genre>> getAllGenres(){
        return ResponseEntity.ok().body(genreService.getAllGenres());
    }
    @GetMapping("/{genreId}")
    public ResponseEntity<Genre> getGenreById(@PathVariable Long genreId){
        return ResponseEntity.ok().body(genreService.getGenreById(genreId));
    }
    @GetMapping("/byName/{genreName}")
    public ResponseEntity<Genre> getGenreByName(@PathVariable String genreName){
        return ResponseEntity.ok().body(genreService.getGenreByName(genreName));
    }
    @PostMapping
    public ResponseEntity<Genre> createGenre(@RequestBody Genre genre){
        return ResponseEntity.ok().body(genreService.createGenre(genre));
    }
    @PutMapping("/update/{genreId}")
    public ResponseEntity<Genre> updateGenre(@PathVariable Long genreId, @RequestBody Genre genre){
        return ResponseEntity.ok().body(genreService.updateGenre(genreId, genre));
    }
    @DeleteMapping("/delete/byId/{genreId}")
    public ResponseEntity<String> deleteGenre(@PathVariable Long genreId){
        try {
            genreService.deleteGenre(genreId);
            return ResponseEntity.ok("successfully deleted");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGenreByName(@RequestParam String genreName){
        System.out.println("received request to delete a genre by name: " + genreName);
        try {
            genreService.deleteGenreByName(genreName);
            return ResponseEntity.ok("successfully deleted");
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("genre not found with name: " + genreName + " !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
