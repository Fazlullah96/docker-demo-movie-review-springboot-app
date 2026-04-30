package com.example.docker_demo_springboot_app.service;

import com.example.docker_demo_springboot_app.component.Mapper;
import com.example.docker_demo_springboot_app.dtos.MovieRequest;
import com.example.docker_demo_springboot_app.dtos.MovieResponse;
import com.example.docker_demo_springboot_app.dtos.MovieReviewListResponse;
import com.example.docker_demo_springboot_app.exceptions.MovieAlreadyExistsException;
import com.example.docker_demo_springboot_app.exceptions.MovieNotFoundException;
import com.example.docker_demo_springboot_app.model.Movie;
import com.example.docker_demo_springboot_app.repo.MovieRepo;
import com.example.docker_demo_springboot_app.repo.ReviewRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MovieService {
    private final MovieRepo movieRepo;
    private final ReviewRepo reviewRepo;
    private final Mapper mapper;

    @Transactional
    @Caching(put = {
            @CachePut(value = "MOVIE_CACHE", key = "#result.id")
    }, evict = {
            @CacheEvict(value = "MOVIE_LIST_CACHE", allEntries = true)
    })
    public MovieResponse addMovie(MovieRequest request){
        boolean isMovieAlreadyExistsByTitle = movieRepo.existsByTitle(request.getTitle());
        if(isMovieAlreadyExistsByTitle){
            throw new MovieAlreadyExistsException("Movie Already Exists by Title: " + request.getTitle());
        }
        Movie movie = movieRepo.save(mapper.toMovieModel(request));
        Integer totalReviews = reviewRepo.countByMovieId(movie.getId());
        Double averageRating = reviewRepo.getAverageRatingByMovieId(movie.getId());
        return mapper.toMovieResponse(movie, totalReviews, averageRating);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "MOVIE_CACHE", key = "#id")
    public MovieResponse getMovieById(int id){
        Movie movie = movieRepo.findById(id).orElseThrow(() -> new MovieNotFoundException("Movie Not found for id: " + id));
        Integer totalReviews = reviewRepo.countByMovieId(id);
        Double averageRatings = reviewRepo.getAverageRatingByMovieId(id);
        return mapper.toMovieResponse(movie, totalReviews, averageRatings);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "MOVIE_LIST_CACHE", key = "'ALL_MOVIE'")
    public List<MovieResponse> getAllMovies(){
        List<Movie> movies = movieRepo.findAll();
        return movies.stream().map(movie -> {
            Integer totalReviews = reviewRepo.countByMovieId(movie.getId());
            Double averageRatings = reviewRepo.getAverageRatingByMovieId(movie.getId());
            return mapper.toMovieResponse(movie, totalReviews, averageRatings);
        }).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "MOVIE_REVIEW_CACHE", key = "#id")
    public MovieReviewListResponse getMoviesByReview(int id){
        Movie movie = movieRepo.findByIdWithReviews(id).orElseThrow(() -> new MovieNotFoundException("Movie Not found for Id: " + id));
        return mapper.toMovieReviewList(movie);
    }
}
