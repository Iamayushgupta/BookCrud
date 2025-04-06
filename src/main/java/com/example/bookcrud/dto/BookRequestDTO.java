package com.example.bookcrud.dto;

import lombok.Data;
import jakarta.validation.constraints.*;

@Data
public class BookRequestDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

    @NotNull(message = "Author ID is required")
    private Long authorId;
}
