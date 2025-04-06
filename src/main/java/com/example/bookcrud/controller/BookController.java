package com.example.bookcrud.controller;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.response.ApiResponse;
import com.example.bookcrud.service.BookService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private static final Logger log = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService bookService;

    @PostMapping
    public ApiResponse<BookResponseDTO> createBook(@Valid @RequestBody BookRequestDTO book) {
        log.info("Received request to create book: {}", book);
        BookResponseDTO created = bookService.create(book);
        return ApiResponse.<BookResponseDTO>builder()
                .success(true)
                .message("Book created successfully")
                .data(created)
                .build();
    }

    @GetMapping("/paginated")
    public ApiResponse<Page<BookResponseDTO>> getPaginatedBooks(Pageable pageable) {
        Page<BookResponseDTO> page = bookService.getPaginated(pageable);
        return ApiResponse.<Page<BookResponseDTO>>builder()
                .success(true)
                .message("Books fetched with pagination")
                .data(page)
                .build();
    }

    @GetMapping
    public ApiResponse<List<BookResponseDTO>> getAllBooks() {
        log.info("Received request to fetch all books");
        List<BookResponseDTO> books = bookService.getAll();
        return ApiResponse.<List<BookResponseDTO>>builder()
                .success(true)
                .message("Books fetched successfully")
                .data(books)
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponseDTO> getBookById(@PathVariable Long id) {
        log.info("Received request to fetch book by id: {}", id);
        BookResponseDTO book = bookService.getById(id);
        return ApiResponse.<BookResponseDTO>builder()
                .success(true)
                .message("Book fetched successfully")
                .data(book)
                .build();
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponseDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookRequestDTO book) {
        log.info("Received request to update book with id {}: {}", id, book);
        BookResponseDTO updated = bookService.update(id, book);
        return ApiResponse.<BookResponseDTO>builder()
                .success(true)
                .message("Book updated successfully")
                .data(updated)
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        log.info("Received request to delete book with id: {}", id);
        bookService.delete(id);
        return ApiResponse.<Void>builder()
                .success(true)
                .message("Book deleted successfully")
                .data(null)
                .build();
    }
}
