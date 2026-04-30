package com.example.docker_demo_springboot_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieReviewListResponse {
    private Integer id;
    private String title;
    private String director;
    private String description;
    private Integer releaseYear;
    private List<ReviewResponse> reviews;
}
