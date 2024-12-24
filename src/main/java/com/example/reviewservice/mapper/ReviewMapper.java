package com.example.reviewservice.mapper;

import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.DetailReviewDTO;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ReviewMapper {


    int insertReview(@Valid Review review);

    DetailReviewDTO selectReviewById(Long id);

    void deleteByIds(List<Long> ids);

    void updateReview(Review review);

    List<Review> selectReviewsList();
}
