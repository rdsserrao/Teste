package com.example.book.controller;

import com.example.book.model.Movie;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private List<Movie> movies = new ArrayList<>();

    public MovieController() {
        this.movies.add(new Movie("Avatar", 2009, "James Cameron"));
        this.movies.add(new Movie("I Am Legend", 2007, "Francis Lawrence"));
        this.movies.add(new Movie("300", 2006, "Zack Snyder"));
        this.movies.add(new Movie("The Avengers", 2012, "Joss Whedon"));
    }

    @GetMapping
    public List<Movie> findAll() {
        return movies;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public List<Movie> create(@RequestBody Movie movie) {
        movies.add(movie);
        return movies;
    }

}
