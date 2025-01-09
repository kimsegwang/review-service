package com.example.reviewservice.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Column;

@Getter
@Setter
public class ReviewAllMyListDTO {
    private Long id;
    private String order_id;
    private String product_info;
}
