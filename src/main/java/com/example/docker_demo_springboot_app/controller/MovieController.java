package com.example.docker_demo_springboot_app.controller;

import com.example.docker_demo_springboot_app.dtos.MovieRequest;
import com.example.docker_demo_springboot_app.dtos.MovieResponse;
import com.example.docker_demo_springboot_app.dtos.MovieReviewListResponse;
import com.example.docker_demo_springboot_app.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movie")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> addMovie(@RequestBody MovieRequest request){
        return new ResponseEntity<>(movieService.addMovie(request), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> getMovieById(@PathVariable int id){
        return new ResponseEntity<>(movieService.getMovieById(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> getAllMovies(){
        return new ResponseEntity<>(movieService.getAllMovies(), HttpStatus.OK);
    }

    @GetMapping("/reviews/{id}")
    public ResponseEntity<MovieReviewListResponse> getAllReviewsByMovieId(@PathVariable int id){
        return new ResponseEntity<>(movieService.getMoviesByReview(id), HttpStatus.OK);
    }
}
