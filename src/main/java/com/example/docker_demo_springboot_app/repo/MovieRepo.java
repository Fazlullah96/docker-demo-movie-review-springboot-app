package com.example.docker_demo_springboot_app.repo;

import com.example.docker_demo_springboot_app.model.Movie;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MovieRepo extends JpaRepository<Movie, Integer> {
    boolean existsByTitle(String title);
    @Query("SELECT m FROM Movie m LEFT JOIN m.reviews WHERE m.id = :id")
    Optional<Movie> findByIdWithReviews(@Param("id") int id);
}
