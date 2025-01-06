package com.example.reviewservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class DetailReviewDTO {
    private Long id;
    private String title;
    private String content;
    private String authorId;
    private String img;
    private double rating;
    private Instant createdAt;
}
