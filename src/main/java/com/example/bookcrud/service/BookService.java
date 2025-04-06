package com.example.bookcrud.service;

import com.example.bookcrud.dto.BookRequestDTO;
import com.example.bookcrud.dto.BookResponseDTO;
import com.example.bookcrud.exception.BookNotFoundException;
import com.example.bookcrud.model.Author;
import com.example.bookcrud.model.Book;
import com.example.bookcrud.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorService authorService;

    public BookResponseDTO create(BookRequestDTO request) {
        log.debug("Creating book with data: {}", request);

        Author author = authorService.getById(request.getAuthorId());

        Book book = Book.builder()
                .title(request.getTitle())
                .author(author)
                .price(request.getPrice())
                .build();

        Book saved = bookRepository.save(book);

        log.info("Book created with ID: {}", saved.getId());
        return toResponseDTO(saved);
    }

    public Page<BookResponseDTO> getPaginated(Pageable pageable) {
        log.debug("Fetching paginated books");
        return bookRepository.findAll(pageable)
                .map(this::toResponseDTO);
    }

    public List<BookResponseDTO> getAll() {
        log.debug("Fetching all books from DB");
        return bookRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    public BookResponseDTO getById(Long id) {
        log.debug("Fetching book by ID: {}", id);
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found with id {}", id);
                    return new BookNotFoundException("Book not found with id " + id);
                });
        return toResponseDTO(book);
    }

    public BookResponseDTO update(Long id, BookRequestDTO updated) {
        log.debug("Updating book ID: {}", id);

        Book book = bookRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Book not found for update with id {}", id);
                    return new BookNotFoundException("Book not found with id " + id);
                });

        Author author = authorService.getById(updated.getAuthorId());

        book.setTitle(updated.getTitle());
        book.setPrice(updated.getPrice());
        book.setAuthor(author);

        Book updatedBook = bookRepository.save(book);
        log.info("Book updated with ID: {}", id);

        return toResponseDTO(updatedBook);
    }

    public void delete(Long id) {
        log.debug("Deleting book with ID: {}", id);
        bookRepository.deleteById(id);
        log.info("Book with ID {} deleted", id);
    }

    private BookResponseDTO toResponseDTO(Book book) {
        return BookResponseDTO.builder()
                .id(book.getId())
                .title(book.getTitle())
                .price(book.getPrice())
                .authorName(book.getAuthor().getName())
                .build();
    }
}
