package com.example.reviewservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DetailReviewDTO {
    private String title;
    private String content;
    private double rating;
    private String img;
}
