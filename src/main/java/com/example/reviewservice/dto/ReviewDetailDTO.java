package com.example.reviewservice.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReviewDetailDTO {
    private String title;
    private String content;
    private double rating;
    private List<String> img;
}
