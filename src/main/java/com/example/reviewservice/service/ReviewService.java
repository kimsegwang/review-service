package com.example.reviewservice.service;

import com.example.reviewservice.S3.S3Service;
import com.example.reviewservice.client.FileClient;
import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.*;
import com.example.reviewservice.mapper.ReviewMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewMapper reviewMapper;
    private final FileClient fileClient;
    private final S3Service s3service;

    //전체리뷰 리스트
// 페이징된 리뷰 리스트 반환
    public List<Review> getPagedReviews(int start, int pageSize) {
        return reviewMapper.selectPagedReviews(start, pageSize);
    }

    // 전체 리뷰 수 반환 (페이징을 위해)
    public int getReviewCount() {
        return reviewMapper.selectReviewCount();
    }

    //작성 가능한 개인리뷰 리스트
    public List<MyReviewOrderIdListDTO> SelectMyReviewsList(String id) {
        System.out.println("서비스1 "+id);
        return reviewMapper.selectMyReviewOrderIdListList(id);
    }

    //작성한 개인리뷰 리스트
    public List<ReviewAllMyListDTO> SelectMyAllReviewsList(String id) {
        System.out.println("서비스 "+id);
        return reviewMapper.selectMyAllReviewOrderIdListList(id);
    }




    // 이미지 경로를 세미콜론으로 구분하여 List<String>으로 변환하는 메서드
    public List<String> parseImagePaths(String img) {
        if (img != null && !img.isEmpty()) {
            return Arrays.asList(img.split(";"));
        }
        return Collections.emptyList();
    }

    @Transactional
    public ReviewCreateDTO createReview(ReviewCreateDTO review, List<MultipartFile> images) throws IOException {
        if (images != null) {
            System.out.println("이미지는"+images);
            StringBuilder s3Urls = new StringBuilder();
            for (MultipartFile file : images) {
                String s3Url = s3service.uploadFile(file);
                if (s3Urls.length() > 0) {
                    s3Urls.append(";");  // 이미지 URL을 세미콜론으로 구분
                }
                s3Urls.append(s3Url);
            }
            review.setImg(s3Urls.toString()); // 여러 이미지 URL을 세미콜론으로 구분하여 저장
        }

        int result = reviewMapper.insertReview(review);
        if (result > 0) {
            return review;
        } else {
            throw new RuntimeException("리뷰 등록 실패");
        }
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
        System.out.println("arr ::" + Arrays.toString(arr));
        List<String> imgPaths = Arrays.asList(arr);
        System.out.println(imgPaths);
        List<String> imgList = List.of();
        if (!imgPaths.getFirst().isEmpty()) {
            imgList = fileClient.getImg(imgPaths);
            System.out.println("이미지 리스트는 :: "+imgList);
        }
        // 3. FeignClient를 사용해 이미지 정보 가져오기
       
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
    public void removeReview(Long id) {
            reviewMapper.deleteByIds(id);
    }





}
