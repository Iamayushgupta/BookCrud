package com.example.bookcrud.controller;

import com.example.bookcrud.dto.AuthorRequestDTO;
import com.example.bookcrud.dto.AuthorResponseDTO;
import com.example.bookcrud.model.Author;
import com.example.bookcrud.response.ApiResponse;
import com.example.bookcrud.service.AuthorService;
import com.example.bookcrud.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    @PostMapping
    public ApiResponse<AuthorResponseDTO> createAuthor(@RequestBody AuthorRequestDTO author) {
        log.info("Received request to create author : {}",author);

        return ApiResponse.<AuthorResponseDTO>builder()
                .success(true)
                .message("Author created")
                .data(authorService.create(author))
                .build();
    }
}
