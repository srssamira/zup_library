package com.library.zup_library.services.authors;

import com.library.zup_library.controllers.dtos.authors.AuthorRegisterDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorResponseDTO;
import com.library.zup_library.controllers.dtos.authors.AuthorUpdateDTO;
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

    @Override
    public Author saveAuthor(AuthorRegisterDTO authorRegisterDTO) {
        List<Book> books = findBooksByIds(authorRegisterDTO.getBooksId());
        return authorRepository.save(AuthorMapper.toAuthor(authorRegisterDTO, books));
    }

    @Override
    public AuthorResponseDTO findAuthorById(Long authorId) {
        Author author = searchAuthor(authorId);
        return AuthorMapper.toAuthorResponseDTO(author);
    }

    @Override
    public Author updateAuthor(AuthorUpdateDTO authorToUpdate, Long id) {
        Author author = searchAuthor(id);

        updateAuthorFields(author, authorToUpdate);

        List<Book> updatedBooks = findBooksByIds(authorToUpdate.getBooks());
        author.setBooks(updatedBooks);

        return authorRepository.save(author);
    }

    @Override
    public void deleteAuthor(Long authorId) {
        if (!authorRepository.existsById(authorId)) {
            throw new RuntimeException("author not found");
        }
        authorRepository.deleteById(authorId);
    }

    private Author searchAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new RuntimeException("author not found"));
    }

    private List<Book> findBooksByIds(List<Long> booksId) {
        return Optional.ofNullable(booksId).orElse(Collections.emptyList())
                .stream()
                .map(bookId -> bookRepository.findById(bookId)
                        .orElseThrow(() -> new RuntimeException("book not found")))
                .collect(Collectors.toList());
    }

    private void updateAuthorFields(Author author, AuthorUpdateDTO authorToUpdate) {
        if (!author.getName().equals(authorToUpdate.getName())) {
            author.setName(authorToUpdate.getName());
        }

        if (!author.getLastName().equals(authorToUpdate.getLastName())) {
            author.setLastName(authorToUpdate.getLastName());
        }

        if (author.getYearOfBirth() != authorToUpdate.getYearOfBirth()) {
            author.setYearOfBirth(authorToUpdate.getYearOfBirth());
        }

        if (author.getYearOfDeath() != authorToUpdate.getYearOfDeath()) {
            author.setYearOfDeath(authorToUpdate.getYearOfDeath());
        }
    }
}
