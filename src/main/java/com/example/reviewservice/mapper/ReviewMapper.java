package com.example.reviewservice.mapper;

import com.example.reviewservice.domain.Review;
import com.example.reviewservice.dto.DetailReviewDTO;
import com.example.reviewservice.dto.MyReviewOrderIdListDTO;
import com.example.reviewservice.dto.ReviewCreateDTO;
import jakarta.validation.Valid;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
@Transactional
public interface ReviewMapper {


    int insertReview(@Valid ReviewCreateDTO review);

    DetailReviewDTO selectReviewById(Long id);

    void deleteByIds(List<Long> ids);

    void updateReview(Review review);

    List<Review> selectReviewsList();

    List<MyReviewOrderIdListDTO> selectMyReviewOrderIdListList(String id);
}
