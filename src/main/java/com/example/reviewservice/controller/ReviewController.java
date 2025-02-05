package com.example.reviewservice.controller;


import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.*;
import com.example.reviewservice.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    //리뷰 작성
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ReviewCreateDTO> post(
            @RequestPart("title") String title,
            @RequestPart("content") String content,
            @RequestParam("rating") double rating,
            @RequestPart("userId") String userId,
            @RequestPart("orderId") String orderId,
            @RequestPart("productName") String productName,
            @RequestPart(value = "images", required = false) List<MultipartFile> images // images는 선택적으로 받음
    ) throws IOException {
        System.out.println("이미지:::"+images);
        ReviewCreateDTO review = new ReviewCreateDTO();
        review.setTitle(title);
        review.setContent(content);
        review.setRating(rating);
        review.setAuthorId(userId);
        review.setOrderId(orderId);
        review.setProductName(productName);

        ReviewCreateDTO createdReview = reviewService.createReview(review, images);
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
    public void delete(@RequestBody Long id) {
        reviewService.removeReview(id);
    }



    //전체 리뷰
    @GetMapping
    public ReviewListDTO getReviews(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        try {
            // start와 end 계산
            int start = Math.max(0, (page - 1) * pageSize);

            // 서비스에서 페이징된 리뷰 데이터 가져오기
            List<Review> reviews = reviewService.getPagedReviews(start, pageSize);

            // 전체 리뷰 수를 가져와서 총 페이지 계산
            int reviewNum = reviewService.getReviewCount();
            int allPage = (int) Math.ceil((double) reviewNum / pageSize);

            // DTO 반환
            return ReviewListDTO.builder()
                    .reviewList(reviews)
                    .reviewNum(reviewNum)
                    .allPage(allPage)
                    .pageSize(pageSize)
                    .page(page)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("리뷰 목록 처리 중 오류 발생", e);
        }
    }


    //작성 가능한 개인 전체 리뷰
    @GetMapping("/myReview")
    public ReviewMyListDTO getMyReviews(
            @RequestParam String id,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("id =="+id);
        try {
            int start = Math.max(0, (page - 1) * pageSize);
            List<MyReviewOrderIdListDTO> allNewsList = reviewService.SelectMyReviewsList(id);
            int newsNum = allNewsList.size();
            int allPage = (int) Math.ceil((double) newsNum / pageSize);

            int end = Math.min(start + pageSize, newsNum);

            if (start >= newsNum) {
                return ReviewMyListDTO.builder()
                        .myReviewOrderIdList(List.of())
                        .reviewNum(newsNum)
                        .allPage(allPage)
                        .pageSize(pageSize)
                        .page(page)
                        .build();
            }

            List<MyReviewOrderIdListDTO> paginatedNewsList = allNewsList.subList(start, end);

            ReviewMyListDTO build = ReviewMyListDTO.builder()
                    .myReviewOrderIdList(paginatedNewsList)
                    .reviewNum(newsNum)
                    .allPage(allPage)
                    .pageSize(pageSize)
                    .page(page)
                    .build();

            System.out.println("build :: " + build);


            return build;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("뉴스 목록 처리 중 오류 발생", e);
        }
    }

    //작성한 개인 전체 리뷰
    @GetMapping("/myReviewList")
    public AllReviewMyListDTO getMyReviewsList(
            @RequestParam String id,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        System.out.println("id =="+id);
        try {
            int start = Math.max(0, (page - 1) * pageSize);
            List<ReviewAllMyListDTO> allNewsList = reviewService.SelectMyAllReviewsList(id);
            for(ReviewAllMyListDTO review : allNewsList) {
                System.out.println("이게 상품"+review.getProduct_info());
                System.out.println("이게 주문번호"+review.getOrder_id());

            }
            int newsNum = allNewsList.size();
            int allPage = (int) Math.ceil((double) newsNum / pageSize);

            int end = Math.min(start + pageSize, newsNum);

            if (start >= newsNum) {
                return AllReviewMyListDTO.builder()
                        .myReviewList(List.of())
                        .reviewNum(newsNum)
                        .allPage(allPage)
                        .pageSize(pageSize)
                        .page(page)
                        .build();
            }

            List<ReviewAllMyListDTO> paginatedNewsList = allNewsList.subList(start, end);
            System.out.println(paginatedNewsList.get(0).getOrder_id());

            AllReviewMyListDTO build = AllReviewMyListDTO.builder()
                    .myReviewList(paginatedNewsList)
                    .reviewNum(newsNum)
                    .allPage(allPage)
                    .pageSize(pageSize)
                    .page(page)
                    .build();

            System.out.println("build :: " + build);


            return build;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("뉴스 목록 처리 중 오류 발생", e);
        }
    }


}
