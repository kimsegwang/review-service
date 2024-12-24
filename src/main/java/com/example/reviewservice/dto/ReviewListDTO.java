package com.example.reviewservice.dto;

import com.example.reviewservice.domain.Review;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReviewListDTO {
    private List<Review> reviewList;
    private int reviewNum;
    private int allPage;
    private int pageSize;
    private int page;

}
