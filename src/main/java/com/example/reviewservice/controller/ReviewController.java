package com.example.reviewservice.controller;


import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.ReviewDetailDTO;
import com.example.reviewservice.dto.ReviewListDTO;
import com.example.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 작성
    @PostMapping
    public ResponseEntity<Review> post(@Valid @RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
    }

    //리뷰 상세
    @GetMapping("/detail")
    public ReviewDetailDTO getNewsId(@RequestParam Long id) {
        return reviewService.getReviewId(id);
    }


    //리뷰 삭제
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody List<Long> ids) {
        reviewService.removeReview(ids);
    }


    //리뷰 수정
    @PutMapping
    public void put(@Valid @RequestBody Review review) {
        reviewService.updateReview(review);
    }

    //전체 리뷰
    @GetMapping
    public ReviewListDTO getReviews(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            int start = Math.max(0, (page - 1) * pageSize);
            List<Review> allNewsList = reviewService.SelectReviewsList();
            int newsNum = allNewsList.size();
            int allPage = (int) Math.ceil((double) newsNum / pageSize);

            int end = Math.min(start + pageSize, newsNum);

            if (start >= newsNum) {
                return ReviewListDTO.builder()
                        .reviewList(List.of())
                        .reviewNum(newsNum)
                        .allPage(allPage)
                        .pageSize(pageSize)
                        .page(page)
                        .build();
            }

            List<Review> paginatedNewsList = allNewsList.subList(start, end);

            return ReviewListDTO.builder()
                    .reviewList(paginatedNewsList)
                    .reviewNum(newsNum)
                    .allPage(allPage)
                    .pageSize(pageSize)
                    .page(page)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("뉴스 목록 처리 중 오류 발생", e);
        }
    }

    //개인 전체 리뷰


}
