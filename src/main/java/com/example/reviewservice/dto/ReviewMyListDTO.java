package com.example.reviewservice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Builder
@ToString
public class ReviewMyListDTO {
    private List<MyReviewOrderIdListDTO> myReviewOrderIdList ;
    private int reviewNum;         // 전체 리뷰 개수
    private int allPage;         // 총 페이지 수
    private int pageSize;        // 페이지당 리뷰 수
    private int page;
}
