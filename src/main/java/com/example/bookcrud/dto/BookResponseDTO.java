package com.example.bookcrud.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {
    private Long id;
    private String title;
    private double price;
    private String authorName;
}

