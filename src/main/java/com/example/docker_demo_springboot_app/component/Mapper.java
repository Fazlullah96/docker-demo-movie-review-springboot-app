package com.example.docker_demo_springboot_app.component;

import com.example.docker_demo_springboot_app.dtos.*;
import com.example.docker_demo_springboot_app.model.Movie;
import com.example.docker_demo_springboot_app.model.Review;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class Mapper {
    public Movie toMovieModel(MovieRequest request){
        return Movie
                .builder()
                .title(request.getTitle())
                .director(request.getDirector())
                .description(request.getDescription())
                .releaseYear(request.getReleaseYear())
                .genre(request.getGenre())
                .build();
    }

    public MovieResponse toMovieResponse(Movie request, Integer totalReview, Double averageRating){
        return MovieResponse
                .builder()
                .id(request.getId())
                .title(request.getTitle())
                .director(request.getDirector())
                .description(request.getDescription())
                .releaseYear(request.getReleaseYear())
                .totalReviews(totalReview)
                .averageRating(averageRating)
                .build();
    }


    public Review toReviewModel(ReviewRequest request, Movie movie){
        return Review
                .builder()
                .reviewerName(request.getReviewerName())
                .movie(movie)
                .comment(request.getComment())
                .rating(request.getRating())
                .build();
    }

    public ReviewResponse toReviewResponse(Review request){
        return ReviewResponse
                .builder()
                .id(request.getId())
                .reviewerName(request.getReviewerName())
                .rating(request.getRating())
                .comment(request.getComment())
                .movieId(request.getMovie().getId())
                .createdAt(request.getCreatedAt())
                .build();
    }


    public MovieReviewListResponse toMovieReviewList(Movie request){
        return MovieReviewListResponse
                .builder()
                .id(request.getId())
                .title(request.getTitle())
                .director(request.getDirector())
                .description(request.getDescription())
                .releaseYear(request.getReleaseYear())
                .reviews(request.getReviews().stream().map(this::toReviewResponse).collect(Collectors.toList()))
                .build();
    }
}
