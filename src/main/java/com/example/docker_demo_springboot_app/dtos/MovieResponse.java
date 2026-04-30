package com.example.docker_demo_springboot_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieResponse {
    private Integer id;
    private String title;
    private String director;
    private String description;
    private Integer releaseYear;

    private Double averageRating;
    private Integer totalReviews;
}
