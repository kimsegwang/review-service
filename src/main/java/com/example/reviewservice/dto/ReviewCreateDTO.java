package com.example.reviewservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.relational.core.mapping.Column;

import java.time.Instant;

@Getter
@Setter
public class ReviewCreateDTO {

    private Long id;

    private String title;

    private String content;

    @Column("author_id")
    private String authorId;

    @Column("img") // 여러 이미지 경로를 ';'로 연결
    private String img;

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
