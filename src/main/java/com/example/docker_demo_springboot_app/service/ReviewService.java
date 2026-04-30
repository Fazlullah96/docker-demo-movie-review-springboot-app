package com.example.docker_demo_springboot_app.service;

import com.example.docker_demo_springboot_app.component.Mapper;
import com.example.docker_demo_springboot_app.dtos.ReviewRequest;
import com.example.docker_demo_springboot_app.dtos.ReviewResponse;
import com.example.docker_demo_springboot_app.exceptions.MovieNotFoundException;
import com.example.docker_demo_springboot_app.exceptions.ReviewAlreadyExistsException;
import com.example.docker_demo_springboot_app.model.Movie;
import com.example.docker_demo_springboot_app.model.Review;
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
public class ReviewService {
    private final ReviewRepo reviewRepo;
    private final MovieRepo movieRepo;
    private final Mapper mapper;

    @Transactional
    @Caching(put = {
            @CachePut(value = "REVIEW_CACHE", key = "#result.id")
    }, evict = {
            @CacheEvict(value = "MOVIE_LIST_CACHE", allEntries = true),
            @CacheEvict(value = "MOVIE_REVIEW_LIST_CACHE", allEntries = true)
    })
    public ReviewResponse addReview (ReviewRequest request){
        Movie movie = movieRepo
                .findById(request
                        .getMovieId())
                .orElseThrow(() -> new MovieNotFoundException("Movie Not Found for Id: " + request.getMovieId()));
        boolean existsByMovieIdAndReviewer = reviewRepo
                .existsByMovieIdAndReviewerName(request.getMovieId(), request.getReviewerName());
        if(existsByMovieIdAndReviewer){
            throw new ReviewAlreadyExistsException("Review Already Exists with MovieId: " + request.getMovieId() + " or ReviewName: " + request.getReviewerName());
        }

        Review review = reviewRepo.save(mapper.toReviewModel(request, movie));
        return mapper.toReviewResponse(review);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "MOVIE_REVIEW_LIST_CACHE", key = "#movieId")
    public List<ReviewResponse> getAllReviewsByMovieId(int movieId){
        boolean exists = movieRepo.existsById(movieId);
        if(!exists){
            throw new MovieNotFoundException("Movie Not Found For Id: " + movieId);
        }

        List<Review> reviews = reviewRepo.findAllByMovieId(movieId);
        return reviews
                .stream()
                .map(mapper::toReviewResponse)
                .collect(Collectors.toList());
    }
}
