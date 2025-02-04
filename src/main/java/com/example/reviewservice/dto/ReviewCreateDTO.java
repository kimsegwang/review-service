package com.example.reviewservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Getter
@Setter
public class ReviewCreateDTO {

    private Long id;
    private String title;
    private String content;

    @Column("author_id")
    private String authorId;

    @Column("img")// S3에서 반환된 이미지 URL 저장
    private String img;

    private MultipartFile[] imgFile; // 여러 파일을 업로드할 수 있도록 배열로 수정

    @Column("rating")
    private double rating;

    @CreatedDate
    @Column("created_at")
    private Instant createdAt;

    @Column("order_id")
    private String orderId;

    @Column("product_info")
    private String productName;
}

