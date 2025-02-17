package com.example.reviewservice.mapper;

import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.DetailReviewDTO;
import com.example.reviewservice.dto.MyReviewOrderIdListDTO;
import com.example.reviewservice.dto.ReviewAllMyListDTO;
import com.example.reviewservice.dto.ReviewCreateDTO;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface ReviewMapper {


    int insertReview(ReviewCreateDTO review);

    DetailReviewDTO selectReviewById(Long id);

    void deleteByIds(Long id);

    List<Review> selectReviewsList();

    List<MyReviewOrderIdListDTO> selectMyReviewOrderIdListList(String id);

    List<ReviewAllMyListDTO> selectMyAllReviewOrderIdListList(String id);

    List<Review> selectPagedReviews(int start, int pageSize);

    int selectReviewCount();
}
