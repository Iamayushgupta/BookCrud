package com.example.bookcrud.service;

import com.example.bookcrud.dto.AuthorRequestDTO;
import com.example.bookcrud.dto.AuthorResponseDTO;
import com.example.bookcrud.exception.AuthorNotFoundException;
import com.example.bookcrud.model.Author;
import com.example.bookcrud.repository.AuthorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    private static final Logger log = LoggerFactory.getLogger(AuthorService.class);

    @Autowired
    private AuthorRepository authorRepository;

    public Author getById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Author not found with id {}", id);
                    return new AuthorNotFoundException("Author not found with id " + id);
                });
    }

    public AuthorResponseDTO create(AuthorRequestDTO request) {
        log.info("Creating author: {}", request.getName());
        Author author = Author.builder()
                .name(request.getName())
                .build();
        return toResponseDTO(authorRepository.save(author));
    }

    public List<AuthorResponseDTO> getAll() {
        log.info("Fetching all authors");
        return authorRepository.findAll().stream()
                .map(this::toResponseDTO)
                .toList();
    }

    private AuthorResponseDTO toResponseDTO(Author author) {
        return AuthorResponseDTO.builder()
                .id(author.getId())
                .name(author.getName())
                .build();
    }
}
