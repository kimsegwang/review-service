package com.example.reviewservice.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Data
public class Review {
    @Id
    private Long id;

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @NotBlank(message = "Content must not be blank.")
    private String content;

    @Column("author_id")
    private Integer authorId;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("rating")
    private Integer rating;

    @Column("img") // 여러 이미지 경로를 ';'로 연결
    private String img;
}
