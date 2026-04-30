package com.example.docker_demo_springboot_app.repo;

import com.example.docker_demo_springboot_app.model.Review;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
    Integer countByMovieId(int id);
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.movie.id = :movieId")
    Double getAverageRatingByMovieId(@Param("movieId") Integer movieId);
    boolean existsByMovieIdAndReviewerName(int movieId, String reviewerName);
    List<Review> findAllByMovieId(int movieId);
}
