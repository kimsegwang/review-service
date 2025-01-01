package com.example.reviewservice.domain;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;

import java.time.LocalDateTime;

@Getter
@Setter
public class Review {
    @Id
    private Long id;

    @NotBlank(message = "Title must not be blank.")
    private String title;

    @NotBlank(message = "Content must not be blank.")
    private String content;

    @Column("author_id")
    private String authorId;

    @CreatedDate
    @Column("created_at")
    private LocalDateTime createdAt;

    @Column("rating")
    private double rating;

    @Column("img") // 여러 이미지 경로를 ';'로 연결
    private String img;
}
