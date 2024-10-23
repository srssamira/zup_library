package com.library.zup_library.services.authors;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.models.Author;
import com.library.zup_library.models.Book;
import com.library.zup_library.repositories.AuthorRepository;
import com.library.zup_library.repositories.BookRepository;
import com.library.zup_library.services.mappers.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;

    public Author saveAuthor(AuthorRegisterDTO authorRegisterDTO) {

        List<Book> books = Optional.ofNullable(authorRegisterDTO.getBooksId()).orElse(Collections.emptyList())
                .stream().map(booksId -> bookRepository.findById(booksId).get())
                .collect(Collectors.toList());
        return authorRepository.save(AuthorMapper.toAuthor(authorRegisterDTO, books));
    }

    public AuthorResponseDTO findAuthorById(Long authorId) {
        Author author = authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("book not found (ID " + authorId + ")"));
        return AuthorMapper.toAuthorResponseDTO(author);
    }
}
