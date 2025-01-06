package com.example.reviewservice.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
public class ReviewDetailDTO {
    private Long id;
    private String title;
    private String content;
    private String authorId;
    private List<String> img;
    private double rating;
    private Instant createdAt;
}
