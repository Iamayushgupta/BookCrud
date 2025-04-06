package com.example.bookcrud.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Standard API response")
public class ApiResponse<T> {

    @Schema(description = "true if request succeeded", example = "true")
    private boolean success;

    @Schema(description = "Message about the result", example = "Book created successfully")
    private String message;

    private T data;
}
