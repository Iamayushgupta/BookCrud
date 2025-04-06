package com.example.bookcrud.repository;

import com.example.bookcrud.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Long> {
}
