package com.example.docker_demo_springboot_app.controller;

import com.example.docker_demo_springboot_app.dtos.ReviewRequest;
import com.example.docker_demo_springboot_app.dtos.ReviewResponse;
import com.example.docker_demo_springboot_app.service.ReviewService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/review")
@Slf4j
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ReviewResponse> addReview(@RequestBody ReviewRequest request){
        return new ResponseEntity<>(reviewService.addReview(request), HttpStatus.CREATED);
    }

    @GetMapping("/movie/{id}")
    public ResponseEntity<List<ReviewResponse>> getAllReviewsByMovieId(@PathVariable int id){
        return new ResponseEntity<>(reviewService.getAllReviewsByMovieId(id), HttpStatus.OK);
    }
}
