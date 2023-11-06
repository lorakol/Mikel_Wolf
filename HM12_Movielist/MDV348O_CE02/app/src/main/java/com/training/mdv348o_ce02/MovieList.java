package com.training.mdv348o_ce02;


import java.util.ArrayList;
import java.util.List;

public class MovieList {
    private List<Movie> movies;

    public MovieList() {
        movies = new ArrayList<>();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public List<Movie> getMovies() {
        return movies;
    }
}

