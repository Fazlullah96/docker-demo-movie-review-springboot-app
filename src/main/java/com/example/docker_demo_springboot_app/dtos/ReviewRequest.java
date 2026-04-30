package com.example.docker_demo_springboot_app.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReviewRequest {
    private String reviewerName;
    private Integer rating;
    private Integer movieId;
    private String comment;
}
