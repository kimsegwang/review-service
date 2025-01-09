package com.example.reviewservice.service;

import com.example.reviewservice.client.FileClient;
import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.DetailReviewDTO;
import com.example.reviewservice.dto.MyReviewOrderIdListDTO;
import com.example.reviewservice.dto.ReviewCreateDTO;
import com.example.reviewservice.dto.ReviewDetailDTO;
import com.example.reviewservice.mapper.ReviewMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final FileClient fileClient;

    //전체리뷰 리스트
    public List<Review> SelectReviewsList() {
        return reviewMapper.selectReviewsList();
    }

    //개인리뷰 리스트
    public List<MyReviewOrderIdListDTO> SelectMyReviewsList(String id) {
        return reviewMapper.selectMyReviewOrderIdListList(id);
    }




    // 이미지 경로를 세미콜론으로 구분하여 List<String>으로 변환하는 메서드
    public List<String> parseImagePaths(String img) {
        if (img != null && !img.isEmpty()) {
            return Arrays.asList(img.split(";"));
        }
        return Collections.emptyList();
    }

    @Transactional
    public ReviewCreateDTO createReview(@Valid ReviewCreateDTO review) {
        List<String> imagePaths = parseImagePaths(review.getImg());
        // 이미지를 세미콜론으로 구분된 하나의 문자열로 결합
        String imgString = String.join(";", imagePaths);
        review.setImg(imgString);  // 뉴스 객체에 결합된 이미지 경로 설정
        // Mapper 호출하여 DB에 저장
        int result = reviewMapper.insertReview(review);
        if (result > 0) {
            return review;
        } else {
            throw new RuntimeException("Failed to insert review");
        }

    }

    //리뷰 수정

    @Transactional
    public void updateReview(Review review) {
        List<String> imagePaths = parseImagePaths(review.getImg());
        String imgString = String.join(";", imagePaths);
        review.setImg(imgString);
        reviewMapper.updateReview(review);
    }

    //상세 리뷰
    public ReviewDetailDTO getReviewId(Long id) {
        // 1. 뉴스 데이터 조회
        DetailReviewDTO ReviewList = reviewMapper.selectReviewById(id);

        // 2. 이미지 경로 추출
//        List<String> imgPaths = newsList.stream()
//                .map(DetailNewsDTO::getImg)
//                .collect(Collectors.toList());

        String[] arr = ReviewList.getImg().split(";");
        List<String> imgPaths = Arrays.asList(arr);

        // 3. FeignClient를 사용해 이미지 정보 가져오기
        List<String> imgList = fileClient.getImg(imgPaths);
        // Products와 인코딩된 이미지를 매칭하여 DTO 리스트를 생성

        return ReviewDetailDTO.builder()
                .id(ReviewList.getId())
                .title(ReviewList.getTitle())
                .content(ReviewList.getContent())
                .rating(ReviewList.getRating())
                .img(imgList)
                .authorId(ReviewList.getAuthorId())
                .createdAt(ReviewList.getCreatedAt())
                .build();
    }


    //리뷰 삭제
    public void removeReview(List<Long> ids) {
        if (ids != null && !ids.isEmpty()) {
            reviewMapper.deleteByIds(ids);
        }
    }





}
